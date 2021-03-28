package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about the classes, namely accessible fields and functions(vtable)
 */
public class ClassInfo {
    String cname;                       // name of class
    String pname;                       // name of parent class(if present)
    List<Field> fields;                 // fields of current class 
    List<Function> functions;           // functions of curr class(includes superclass and subclass)
    List<Function> ref_accessible_fns;  // includes only the functions accessible by an instance of the current class
    int num_refs;                       // number of references of current class
    
    public ClassInfo(String cname) {
        this(cname, null);
    }

    public ClassInfo(String cname, String pname){
        this.cname = cname;
        this.pname = pname;
        this.fields = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.ref_accessible_fns = new ArrayList<>();
        this.num_refs = 0;
    }

    void addMethod(Function f){functions.add(f);ref_accessible_fns.add(f);}

    /**
     * Returns function object of current class
     * @param fname
     * @return
     */
    Function getMethod(String fname){
        Function m = null;
        for(Function f: this.functions){
            if(this.cname.equals(f.cname) && f.fname.equals(fname)){
                m = f;
            }
        }
        return m;
    }

    /**
     * Returns all possible functions accessible by the current class
     * @param fname Function name
     * @return {@code List<Function>}
     */
    List<Function> getMethods(String fname){
        List<Function> flist = new ArrayList<>();
        for(Function f : this.functions){
            if(fname.equals(f.fname)){
                flist.add(f);
            }
        }
        return flist;
    }

    void addField(Field f){fields.add(f);}

    /**
     * Returns the appropriate field object of the current string x
     * @param x
     * @return {@code Field} corresponding to x
     */
    Field getField(String x){
        Field t=null;
        for(Field f : this.fields){
            if(f.name.equals(x)){
                t = f;
                break;
            }
        }
        return t;
    }
    
    /**
     * Check if the function from ancestor or child is overridden in the 
     * current class.
     * @param key  Function from ancestor class
     * @return  {@code true} if current class overrides the function
     */
    boolean isOverridden(Function key){
        boolean flag = false;
        
        for(Function f: this.functions){
            if(key.fname.equals(f.fname)){
                flag = true;
                break;
            }
        }

        return flag;
    }

    /**
     * Checks if function from child is already present in the current 
     * class
     * @return {@code true} or {@code false} 
     */
    boolean isPresent(Function key){
        boolean flag = false;
        
        for(Function f : this.functions){
            if(key.equals(f)){
                flag = true;
                break;
            }
        }

        return flag;
    }

    /**
     * Merges functions overridden in child class with the current set
     * of functions in {@code this.functions}
     * @param c Child class information
     * @return  Flag denoting if value has changed or not
     */
    boolean mergeChildInfo(ClassInfo c){
        boolean change = false;
        
        List<Function> new_functions  = new ArrayList<>(this.functions);

        for(Function fc: c.functions){
            if(this.isOverridden(fc) && !this.isPresent(fc)){
                // fc is necessarily a child overridden fn.
                // add it to this.functions
                new_functions.add(fc);
                change = true;
            }
        }

        this.functions = new_functions;
        return change;
    }

    /**
     * Merges fields of parent class into current class
     * @param p Parent class object
     */
    void mergeAncestorInfo(ClassInfo p) {

        // Merge fields using deep copy
        for(Field f : p.fields) {
            this.fields.add(f.copy());
        }

        // Merge functions using shallow copy
        for(Function f: p.functions) {
            this.functions.add(f);
            this.ref_accessible_fns.add(f);
        }

        this.pruneAncestorEntries();
    }

    /**
     * Prunes overridden functions of ancestor in current class
     */
    void pruneAncestorEntries(){
        
        List<Function> l = new ArrayList<>();
        List<Function> ref_l = new ArrayList<>();

        int n = this.functions.size();
        for(int i = 0; i < n;++i){
            Function fi = this.functions.get(i);
            boolean add = true;
            if(fi.cname.equals(this.cname)){
                l.add(fi);
                ref_l.add(fi);
            }
            else{
                for( int j = 0;j<n;++j){
                    if(j!=i){
                        Function fj = this.functions.get(j);
                        if( fj.fname.equals(fi.fname) && 
                            fj.cname.equals(this.cname)) {
                            // Method is overridden in current class
                            add = false;
                        }
                    }
                }

                if(add){
                    l.add(fi);
                    ref_l.add(fi);
                }
            }
        }

        this.functions = l;
        this.ref_accessible_fns = ref_l;
    }
    
    /**
     * Prints all function and field declarations
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        String sp = "    ";
        sb.append("  ->" + "Variables\n");
        for(Field i : fields) {
            if(i instanceof MemberField){
                sb.append(sp + "- " + i.type + " " + i.name + " [ from "+ ((MemberField)i).cname +" ]\n");
            } else if(i instanceof BasicMemberField){
                sb.append(sp + "- " + i.type + " " + i.name + " [ from "+ ((BasicMemberField)i).cname +" ]\n");
            }
        }
        sb.append("  ->" + "Functions\n");
        for(Function f : functions) {
            sb.append(sp + "- "+f.cname+"::"+ f.fname + "()\n" );
        }
        sb.append("  ->"+"Ref Accessible Functions\n");
        for(Function f : ref_accessible_fns){
            sb.append(sp + "- "+f.cname+"::"+ f.fname + "()\n" );
        }
        return sb.toString();
    }
    
    /**
     * Return number of refs
     * @return
     */
    int getRefId() {
        return ++this.num_refs;
    }
}
