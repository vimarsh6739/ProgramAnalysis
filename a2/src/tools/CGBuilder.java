package tools;

import java.util.*;

/**
 * Driver class for building program call graph
 */
public class CGBuilder {

    List<ClassInfo> clist;                          // Class list
    Map<String, ClassInfo> cMap;                    // Index class map
    ClassInfo curr_class;
    
    List<Function> flist;                           // Function list
    Function curr_fn;
    
    List<Reference> refList;                        // Reference list
    Map<Reference, Map<Field, Lattice >> sigma;     // Heap
    
    /**
     * Public constructor for Call Graph generator
     */
    public CGBuilder() {
        this.clist = new ArrayList<>();
        this.cMap = new HashMap<>();
        this.flist = new ArrayList<>();
        this.refList = new ArrayList<>();
        this.sigma = new HashMap<Reference, Map<Field, Lattice>>();
        this.curr_class=null;
        this.curr_fn=null;
    }

    // Update/add functions

    /**
     * Add a class
     * @param cname Class name
     * @param pname Superclass name
     */
    public void addClass(String cname, String pname){
        curr_class = new ClassInfo(cname, pname);
        clist.add(curr_class);
        cMap.put(cname, curr_class);
    }

    /**
     * Add a function to current class      
     * @param fname Function name
     */
    public void addMethod(String fname){
        curr_fn = new Function(fname, this.curr_class.cname, this.curr_class);
        flist.add(curr_fn);                 // global fn list
        curr_class.addMethod(curr_fn);      // local fn list
    }

    /**
     * Add a member variable
     * @param type Type of the variable
     * @param name Identifier name
     */
    public void addClassField(String type, String name){
        Field t;
        switch(type){
            case "int":
            case "boolean":
                t = new BasicMemberField(type, name, curr_class.cname);
                break;
            default:
                t = new MemberField(type, name, curr_class.cname);
                break;
        }
        curr_class.addField(t);
    }

    /**
     * Add a function local variable
     * @param type Type of the variable
     * @param name Identifier name
     */
    public void addLocalField(String type, String name){
        Field t;
        switch(type) {
            case "int":
            case "boolean":
                t = new BasicField(type, name);
            break;
            default:
                t = new LocalField(type, name, curr_fn.fname);
        }
        curr_fn.addField(t);
    }

    /**
     * Add a statement in the current function to it's object
     * @param op    Operation performed
     * @param x     The result variable
     * @param e1    The first argument
     * @param e2    The second argument
     * @param eargs Args in case of a function call
     */
    public void addStatement(Operations op, String x, String e1, String e2, List<String> eargs) {
        switch(op) {
            case AND:
            case LEQ:
            case ADD:
            case SUB:
            case MULT:
            case ARRAYLOOKUP:
            case ARRAYASSIGN:
                this.curr_fn.addStatement(op, x, e1, e2);
                break;

            case ARRAYLENGTH:
            case ASSIGNCONST:
            case NOT:
                this.curr_fn.addStatement(op, x, e1);
                break;
            
            case ALLOCATE:                
            case ARRAYALLOCATE:
                // Make a new reference & update refList
                ClassInfo c = cMap.get(e1);
                Reference r = new Reference(c);
                refList.add(r);
                this.curr_fn.addStatement(op, x, r);
                break;

            case ASSIGN:
                this.curr_fn.addStatement(op, x, e1);
                break;

            case FCALL:
                this.curr_fn.addStatement(op, x, e1, this.curr_class.getMethod(e2), eargs);
                break;

            case LOAD:
                this.curr_fn.addStatement(op, x, e1, e2);
                break;

            case STORE:
                this.curr_fn.addStatement(op, x, e1, e2);
                break;

            default:
        }
    }
    
    // Inheritance relations and int[] handling

    /**
     * Adds integer array as a valid reference type with no functions or fields 
     */
    void addIntArrayClass(){
        ClassInfo iarr = new ClassInfo("int[]");
        clist.add(iarr);
        cMap.put("int[]", iarr);
    }

    /**
     * Prints all class hierarchy information for debugging
     */
    public void printClassHierarchy(){    
        for(ClassInfo c : this.clist){
            System.out.println("-> "+c.cname);
            c.printMembers();
        }
    }

    /**
     * Recursively add overridden functions of subclass in every superclass
     * @param c Current class for which superclass needs to be updated
     */
    boolean updateSuperClassEntries(ClassInfo c){
        boolean change = false;
        if(c.pname != null){
            ClassInfo p = cMap.get(c.pname);
            change = p.mergeChildInfo(c);
            change |= this.updateSuperClassEntries(p);
        } 
        return change;
    }

    /**
     * Recursively updates subclass entries for fields
     * @param c Current class
     */
    void updateSubClassEntries(ClassInfo c, Map<ClassInfo,Boolean> visited){
        if(!visited.get(c)) {
            // Recursively update parent
            if(c.pname!=null) {
                ClassInfo p = cMap.get(c.pname);
                this.updateSubClassEntries(p, visited);
                
                // Merge fields(deep) + functions(shallow)
                c.mergeAncestorInfo(p);
            }
            
            // Mark as visited
            visited.put(c, true);
        }
    }

    /**
     * Update the functions and fields of both subclasses and superclasses.
     */
    public void buildInheritanceInfo() {
        Map<ClassInfo, Boolean> visited = new HashMap<>(this.clist.size());

        
        for(ClassInfo c : clist){
            visited.put(c, false);
        }
        
        for(ClassInfo c : clist){
            if(!visited.get(c)){
                this.updateSubClassEntries(c, visited);
            }
        }

        // Remove overridden functions from subclasses
        for(ClassInfo c: clist){
            if(c.pname!=null){
                c.pruneAncestorEntries();
            }
        }

        boolean f = false;
        do {
            f = false;
            for(ClassInfo c : clist){
                f |= this.updateSuperClassEntries(c);
            }
        } while(f);        
        
        this.addIntArrayClass();
    }

    // Lattice initialization

    /**
     * Creates and initializes lattice objects for all 
     * functions and references
     */
    public void buildLattice() {
        // Build lattice for all stacks
        for(Function f : this.flist) {
            f.buildLattice(this.refList);
        }
        
        // For all references, update sigma 
        for(Reference r : this.refList){
            r.buildLattice(this.refList);
            this.sigma.put(r, r.refMap);
        }
    }

    /**
     * Print lattice values
     */
    public void printLattice(){
        
        // Print sigma
        System.out.println("Heap:");
        for(Reference r : this.sigma.keySet()) {
            String sp = "    ";
            System.out.println("Reference " + r.cname + "_"+r.ref_id);
            System.out.println(sp+"Locals are ->"+r.printValue());

            Map<Field, Lattice> sigma_r = this.sigma.get(r);
            
            for(Field f : sigma_r.keySet()){
                System.out.print(sp + "("+f.name+" : " + f.type + ")");
                System.out.println(" -> { " + sigma_r.get(f).printValue() + " }" );
            }
        }

        // Print stacks
        System.out.println("Stack:");
        for(Function f : this.flist){
            System.out.println(f.cname+"::"+ f.fname + "()" );
            f.printStack();
        }
    }
}