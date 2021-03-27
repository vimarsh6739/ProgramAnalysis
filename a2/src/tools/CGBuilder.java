package tools;

import java.util.*;
import java.util.stream.Collectors;

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
    Map<Reference, Map<Field, Lattice >> hpts;     // Heap
    String root_cname;                              

    /**
     * Public constructor for Call Graph generator
     */
    public CGBuilder() {
        this.clist = new ArrayList<>();
        this.cMap = new HashMap<>();
        this.flist = new ArrayList<>();
        this.refList = new ArrayList<>();
        this.hpts = new HashMap<Reference, Map<Field, Lattice>>();
        this.curr_class=null;
        this.curr_fn=null;
        this.root_cname=null;
    }

    // Update/add functions
    
    /**
     * Set the root class
     * @param cname
     */
    public void setRootClass(String cname){
        this.root_cname = cname;
    }
    
    /**
     * Set current class
     * @param cname
     */
    public void setCurrClass(String cname){
        this.curr_class = cMap.get(cname);
    }

    /**
     * Set current function
     * @param fname
     */
    public void setCurrFn(String fname){
        this.curr_fn = this.curr_class.getMethod(fname);
    }

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
     * Adds method of main class
     * @param fname  = {@code main()}
     */
    public void addMainMethod(String fname){
        curr_fn = new Function(fname, this.curr_class.cname, this.curr_class);
        flist.add(curr_fn);                 // global fn list
        curr_class.addMethod(curr_fn);      // local fn list
        curr_fn.setMain();
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

    public void addFormalField(String type, String name){
        Field t;
        switch(type) {
            case "int":
            case "boolean":
                t = new BasicField(type, name);
            break;
            default:
                t = new LocalField(type, name, curr_fn.fname);
        }
        curr_fn.addFormalField(t);
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
        Field xf = this.curr_fn.getField(x);
        Field e1f = null;
        Field e2f = null;
        List<Field> eargf= null;
        
        switch(op) {
            case AND:
            case LEQ:
            case ADD:
            case SUB:
            case MULT:
            case ARRAYLOOKUP:
            case ARRAYASSIGN:
                // Will never be null
                e1f = this.curr_fn.getField(e1);
                e2f = this.curr_fn.getField(e2);
                this.curr_fn.addStatement(op, xf, e1f, e2f);
                break;

            case ARRAYLENGTH:
            case ASSIGNCONST:
            case NOT:
                e1f = this.curr_fn.getField(e1);
                this.curr_fn.addStatement(op, xf, e1f);
                break;
            
            case ALLOCATE:                
            case ARRAYALLOCATE:
                // Make a new reference & update refList
                ClassInfo c = cMap.get(e1);
                Reference r = new Reference(c);
                refList.add(r);
                this.curr_fn.addStatement(op, xf, r);
                break;

            case ASSIGN:
                e1f = this.curr_fn.getField(e1);
                this.curr_fn.addStatement(op, xf, e1f);
                break;

            case FCALL:
                // Add all possible function calls as statements
                // Possible as we know the types of every variable currently
                e1f = this.curr_fn.getField(e1);
                
                ClassInfo caller = this.cMap.get(e1f.type);
                List<Function> callerFlist = caller.getMethods(e2);
                eargf = eargs.stream().map(elt -> this.curr_fn.getField(elt)).collect(Collectors.toList());                
                this.curr_fn.addStatement(op, xf, e1f, callerFlist, eargf);
                break;

            case LOAD:
                e1f = this.curr_fn.getField(e1);
                e2f = this.cMap.get(e1f.type).getField(e2);
                this.curr_fn.addStatement(op, xf, e1f, e2f);
                break;

            case STORE:
                e1f = this.cMap.get(xf.type).getField(e1);
                e2f = this.curr_fn.getField(e2);
                this.curr_fn.addStatement(op, xf, e1f, e2f);
                break;

            default:
        }
    }
    
    /**
     * Initializes the return value of the function
     */
    public void addReturn(String ret_val) {
    
        // System.out.println(this.curr_fn.fname + " <- " + ret_val);
        this.curr_fn.setReturnName(ret_val);
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
            System.out.println(c);
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

    // Points to analysis
    
    /**
     * Print all statements recorded for debugging purposes.
     */
    public void printStatements() {
        for(ClassInfo c : this.clist){
            System.out.println("-> "+c.cname);
            for(Function f : c.functions){
                if(f.cname.equals(c.cname)){
                    f.printStatements();
                }
            }
        }
    }

    /**
     * Creates and initializes lattice objects for all 
     * functions and references
     */
    public void buildLattice() {
        // Build lattice for all functions
        for(Function f : this.flist) {
            f.buildLattice(this.refList);
            
            if(!f.isMain) {
                // System.out.println(f.fname);
                // System.out.println(f.ret_val);
                f.setReturnField();
            }
        }
        
        // For all references, update heap 
        for(Reference r : this.refList){
            r.buildLattice(this.refList);
            this.hpts.put(r, r.refMap);
        }

        for(Function f : this.flist){
            f.setHeap(this.hpts);
        }
    }

    /**
     * Print lattice values
     */
    public void printLattice() {
        
        // Print sigma
        System.out.println("Heap:");
        for(Reference r : this.hpts.keySet()) {
            String sp = "\t";
            System.out.println("Reference " + r.cname + "@"+r.ref_id);
            System.out.println(sp+"Fields are -> "+r.printValue());
            sp += "\t";
            Map<Field, Lattice> sigma_r = this.hpts.get(r);
            
            for(Field f : sigma_r.keySet()){
                System.out.println(sp +f.name+" -> {" + sigma_r.get(f) + " }");
            }
        }

        // Print stacks
        System.out.println("Stack:");
        for(Function f : this.flist){
            System.out.println("Function "+f.cname+"::"+ f.fname + "()" );
            f.printStack();
        }
    }

    /**
     * Performs points to analysis and builds points to sets for all variables
     */
    public void buildPointsToSets(boolean showProgress) {
        
        // Initialize worklist
        Queue<Function> worklist = new LinkedList<>();
        for(Function f : this.flist){
            if(f.isMain){worklist.add(f);break;}
        }
        
        int iter = 1;
        // Run algorithm until convergence of values
        while(!worklist.isEmpty()){
            Function f = worklist.poll();
            
            if(showProgress)
                System.out.println("Iter " + iter + ": In "+f.cname+"::"+f.fname);
            iter++;

            worklist.addAll(f.analyze());
            f.updateSummary();
            
            if(f.summaryChange && !f.isMain) {
                // Add all callers of f to worklist
                for(Function g : this.flist){
                    if(g.calls(f)){
                        worklist.add(g);
                    }
                }
            }

            if(f.stackChange){
                // Add function for reanalysis if it's stack changes
                worklist.add(f);
            }

            if(showProgress) {
                printLattice();
                System.out.println("------------------------------------------------");
            }

            f.resetChangeFlags();
        }
    }

    /**
     * Checks if the 2 variables in the current function are aliases or not
     * @param x
     * @param y
     * @return
     */
    public boolean isAlias(String x, String y){
        Field fx = this.curr_fn.getField(x);
        Field fy = this.curr_fn.getField(y);
        return this.curr_fn.checkAlias(fx, fy);
    }
}