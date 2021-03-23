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
    
    public ClassInfo(String cname) {
        this(cname, null);
    }

    public ClassInfo(String cname, String pname){
        this.cname = cname;
        this.pname = pname;
        this.fields = new ArrayList<>();
        this.functions = new ArrayList<>();
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
            if(!this.isOverridden(f))
                this.functions.add(f);
        }
    }
    
    /**
     * Prints all function and field declarations
     */
    void printMembers() {
        String sp = "    ";
        System.out.println("  ->" + "Variables");
        for(Field i : fields){
            System.out.println(sp + "- " + i.type + " " + i.name + " [ class "+ ((MemberField)i).cname +" ]");
        }
        System.out.println("  ->" + "Functions");
        for(Function f : functions){
            System.out.println(sp + "- " + f.fname + "()" + " [ class "+ f.cname +" ]");
        }
    }
}
