package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about the classes, namely accessible fields and functions(vtable)
 */
public class ClassInfo {
    String cname;               // name of class
    String pname;               // name of parent class(if present)
    List<Field> fields;         // fields of current class 
    List<Function> functions;   // functions of curr class(includes superclass)
    int num_refs;               // number of references of current class
    
    public ClassInfo(String cname) {
        this(cname, null);
    }

    public ClassInfo(String cname, String pname){
        this.cname = cname;
        this.pname = pname;
        this.fields = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.num_refs = 0;
    }

    void addMethod(Function f){functions.add(f);}

    Function getMethod(String fname){
        Function m = null;
        for(Function f: this.functions){
            if(this.cname.equals(f.cname) && f.fname.equals(fname)){
                m = f;
            }
        }
        return m;
    }

    void addField(Field f){fields.add(f);}

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
        }

        this.pruneAncestorEntries();
    }

    /**
     * Prunes overridden functions of ancestor in current class
     */
    void pruneAncestorEntries(){
        
        List<Function> l = new ArrayList<>();
        
        int n = this.functions.size();
        for(int i = 0; i < n;++i){
            Function fi = this.functions.get(i);
            boolean add = true;
            if(fi.cname.equals(this.cname)){
                l.add(fi);
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
                }
            }
        }

        this.functions = l;
    }
    
    /**
     * Prints all function and field declarations
     */
    void printMembers() {
        String sp = "    ";
        System.out.println("  ->" + "Variables");
        for(Field i : fields) {
            if(i instanceof MemberField){
                System.out.println(sp + "- " + i.type + " " + i.name + " [ from "+ ((MemberField)i).cname +" ]");
            } else if(i instanceof BasicMemberField){
                System.out.println(sp + "- " + i.type + " " + i.name + " [ from "+ ((BasicMemberField)i).cname +" ]");
            }
        }
        System.out.println("  ->" + "Functions");
        for(Function f : functions) {
            System.out.println(sp + "- "+f.cname+"::"+ f.fname + "()" );
        }
    }
    
    /**
     * Return number of refs
     * @return
     */
    int getRefId() {
        return ++this.num_refs;
    }
}
