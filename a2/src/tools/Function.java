package tools;
import java.util.*;
import java.util.stream.Collectors;

public class Function {

    class Mod {
        Field v;  
        Operations op;
        
        /**
         * Default constructor
         * @param v
         * @param op
         */
        public Mod(Field v, Operations op) {
            this.v = v;
            this.op = op;
        }

        /**
         * Analyze the given statement and update Lattice
         */
        void analyzeStatement(){
            return ;
        }

        @Override
        public String toString() {
            return "";
        }
    }

    /**
     * {@code v = w}
     */
    class Assign extends Mod {
        Field w;

        public Assign(Field v, Field w, Operations op) {
            super(v, op);
            this.w=w;
        }

        @Override
        void analyzeStatement() {
            Lattice lv = pts.get(v);
            Lattice lw = pts.get(w);
            
            // Take meet
            lv.meet(lw);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(v.name + " = " + w.name + " ;");
            return sb.toString();
        }
    }
    
    /**
     * {@code v = w.f}
     */
    class Load extends Mod {        
        Field w;
        Field f;                       // v = w.f

        public Load(Field v, Field w, Field f, Operations op) {
            super(v, op);
            this.f=f;
            this.w=w;
        }
        
        @Override
        void analyzeStatement() {
            
            Lattice lv = pts.get(this.v);
            Lattice lw = null;
            Lattice lw_cp = null;
            Lattice lf = null;
            Lattice lthis = null;
            Lattice lthis_cp = null;

            if(w instanceof LocalField){
                // Has a pts entry
                lw = pts.get(w);
                lw_cp = lw.copy();
                for(Reference g : lw_cp.curr) {
                    // f is of type MemberField
                    lf = g.getLattice(f);

                    // Take union of all values in lf
                    lv.meet(lf);
                }

            } else 
            if(w instanceof MemberField){
                // w is field of this pointer
                lthis = pts.get(fMap.get("this")); 
                lthis_cp = lthis.copy();
                for(Reference r : lthis_cp.curr){
                    // get ref-field
                    lw = r.getLattice(w);
                    lw_cp = lw.copy();
                    for(Reference g : lw_cp.curr){
                        // f is of type MemberField
                        lf = g.getLattice(f);

                        // Take union of all values in lf
                        lv.meet(lf);
                    }
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(v.name + " = ");
            if(w instanceof MemberField)
                sb.append("this.");
            // System.out.println(v.name);
            // System.out.println(w.name);
            // System.out.println(f);
            sb.append(w.name + "." + f.name + " ;");
            return sb.toString();
        }
    }

    /**
     * {@code v.f = w}
     */
    class Store extends Mod {
        Field f;                       // v.f = w
        Field w;

        /**
         * Default constructor for store type statements
         * @param v
         * @param w
         * @param f
         * @param op
         */
        public Store(Field v, Field w, Field f, Operations op) {
            super(v, op);
            this.f=f;
            this.w=w;
        }

        @Override
        void analyzeStatement() {
            Lattice lv = null;
            Lattice lv_cp = null;
            Lattice lf = null;
            Lattice lthis = null;
            // System.out.println(lthis);
            Lattice lthis_cp = null;;
            Lattice lw = pts.get(w);
            
            if(v instanceof LocalField){
                lv = pts.get(v);
                lv_cp = lv.copy();
                for(Reference r : lv_cp.curr){
                    lf = r.getLattice(f);
                    
                    // Update the reference lattice values
                    lf.meet(lw);
                }
            } else 
            if(v instanceof MemberField){
                lthis = pts.get(fMap.get("this"));
                lthis_cp = lthis.copy();
                for(Reference g : lthis_cp.curr){
                    lv = g.getLattice(v);
                    lv_cp = lv.copy();
                    for(Reference r : lv_cp.curr){
                        lf = r.getLattice(f);

                        // Update the reference lattice values
                        lf.meet(lw);
                    }
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(v instanceof MemberField)sb.append("this.");
            sb.append(v.name+"."+f.name+" = "+w.name+" ;");
            return sb.toString();
        }
    }

    /**
     * {@code v.f = w.g}
     * One of v or w is necessarily {@code this}
     */
    class DoubleAssign extends Mod{
        Field f;
        Field w;                        // v.f = w.g
        Field g;

        public DoubleAssign(Operations op, Field v, Field w, Field f, Field g) {
            super(v, op);
            this.f=f;
            this.w=w;
            this.g=g;
        }

        @Override
        void analyzeStatement() {
            Lattice lv = null;
            Lattice lthis = null;
            Lattice lthis_cp = null;
            Lattice lv_cp = null;
            Lattice lw_cp = null;
            Lattice lw = null;
            Lattice lf = null;
            Lattice lg = null;
            // for(String names : Function.this.fMap.keySet()){
            //     System.out.println(names + " -> " + fMap.get(names));
            // }
            // System.out.println("Function"+Function.this.cname + "::"+Function.this.fname);
            // System.out.println(this);
            if(v instanceof LocalField){
                lv = pts.get(v);
                lv_cp = lv.copy();
                // System.out.println(lv);
                if(w instanceof LocalField){
                    lw = pts.get(w);
                    lw_cp = lw.copy();
                    // System.out.println(lw);
                    // Iterate over references in v
                    for(Reference rv : lv_cp.curr){
                        lf = rv.getLattice(f);
                        // System.out.println(f.name + ":"+f.type);
                        // System.out.println("f="+f.name+" -> {"+lf+"}" +" in "+rv.cname+"::"+rv.ref_id);
                        // Iterate over references in w
                        for(Reference rg : lw_cp.curr){
                            lg = rg.getLattice(g);
                            // System.out.println("g is {" +lg+"}"+" in "+rg.cname+"::"+rg.ref_id);
                            // System.out.println(f);
                            // System.out.println(g);
                            // System.out.println(f.equals(g));
                            // System.out.println(rv.equals(rg));
                            // Take meet of lf and lg
                            lf.meet(lg);
                        }
                    }
                }
                else if(w instanceof MemberField){
                    lthis = pts.get(fMap.get("this"));
                    lthis_cp = lthis.copy();
                    for(Reference rt : lthis_cp.curr){
                        lw = rt.getLattice(w);
                        lw_cp = lw.copy();
                        // Iterate over references in v
                        for(Reference rf : lv_cp.curr){
                            lf = rf.getLattice(f);
                            // Iterate over all references in w
                            for(Reference rg : lw_cp.curr){
                                lg = rg.getLattice(g);
                                // Take meet of lf and lg
                                lf.meet(lg);
                            }
                        }
                    }
                }
            } else 
            if(v instanceof MemberField){
                // w is Local
                lw = pts.get(w);
                lw_cp = lw.copy();
                lthis = pts.get(fMap.get("this"));
                lthis_cp = lthis.copy();
                for(Reference rt : lthis_cp.curr){
                    lv = rt.getLattice(v);
                    lv_cp = lv.copy();
                    // Iterate over references in v
                    for(Reference rf : lv_cp.curr){
                        lf = rf.getLattice(f);
                        // Iterate over references in w
                        for(Reference rg : lw_cp.curr){
                            lg = rg.getLattice(g);
                            // Take meet of lf and lg
                            lf.meet(lg);
                        }
                    }
                }
            }
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(v instanceof MemberField)sb.append("this.");
            sb.append(v.name+"."+f.name+" = ");
            if(w instanceof MemberField)sb.append("this.");
            sb.append(w.name+"."+g.name+" ;");
            return sb.toString();
        }
    }

    /**
     * {@code v = new T()}
     */
    class Allocate extends Mod {        
        Reference T;
        
        public Allocate(Field v, Operations op, Reference T) {
            super(v, op);
            this.T=T;
        }

        @Override
        void analyzeStatement() {
            Lattice lv = null;
            if(v instanceof LocalField){
                // Just add the reference to lattice
                lv = pts.get(v);

                // Add the reference to the lattice
                lv.addRef(T);
            } else 
            if(v instanceof MemberField){
                Lattice lthis = pts.get(fMap.get("this"));
                Lattice lthis_cp = lthis.copy();
                for(Reference rt : lthis_cp.curr){
                    lv = rt.getLattice(v);
                    // Add the reference to the field
                    lv.addRef(T);   
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(v instanceof MemberField)sb.append("this.");
            sb.append(v.name +" = new "+T.cname+"() ;");
            return sb.toString();
        }
    }

    /**
     * {@code v = w op x} 
     */
    class BasicBinary extends Mod {                      
        Field w;
        Field x;

        public BasicBinary(Field v, Operations op, Field w, Field x) {
            super(v, op)    ;
            this.w=w;
            this.x=x;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(v.name+" = ");
            sb.append(w.name);
            switch (op) {
                case ADD: 
                    sb.append("+")  ;
                    break;
                case AND:
                    sb.append("&&");
                    break;
                case ARRAYLOOKUP:
                    sb.append(" @index ");
                    break;
                case ASSIGNCONST:
                    sb.append("=");
                    break;
                case LEQ:
                    sb.append("<");
                    break;
                case MULT:
                    sb.append("*");
                    break;
                case SUB:
                    sb.append("-");
                    break;
                default:
                    sb.append(" bop ");
                    break;
            }
            sb.append(x.name+" ;");
            return sb.toString();
        }
    }

    /**
     * {@code v = op w}
     */
    class BasicUnary extends Mod {   
        Field w;

        /**
         * Default constructor for unary operations
         * @param v 
         * @param op
         * @param w
         */
        public BasicUnary(Field v, Operations op, Field w) {
            super(v, op);
            this.w=w;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(v.name+" = ");
            switch (op) {
                case ARRAYLENGTH:
                    sb.append(" arr_length.");
                    break;
                case NOT:
                    sb.append("!");
                    break;
    
                default:
                    sb.append(" uop ");
                    break;
            }
            if(w!=null)
            sb.append(w.name+" ;");
            return sb.toString();
        }
    }

    /**
     * {@code v = w.n(...)}
     */
    class CallLabel extends Mod {
        Field w;
        Function n;                               
        List<Field> args;
        Operations sop;     // whether there is a implicit this in the operation
        boolean addToQueue;

        /**
         * Default constructor for CallLabel
         * @param v Assigned return field
         * @param op Operation type on v
         * @param w Calling object
         * @param sop Operation type on w
         * @param n Function called
         * @param args Arguments of the function
         */
        public CallLabel(Field v, Operations op, Field w, Operations sop, Function n, List<Field> args) {
            super(v, op);
            this.w=w;
            this.sop = sop;
            this.n = n;
            this.args = new ArrayList<>(args);
            this.addToQueue = false;
        }
        
        /**
         * Assign formal parameter at index to arg
         * @param i Index
         * @param actual The current actual parameter
         */
        void assignFormal(int i, Field actual){
            Lattice lformal = null;
            Lattice lactual = null;
            if(actual instanceof LocalField){
                lformal = n.pts.get(n.fields.get(i));
                lactual = pts.get(actual);

                // update lformal
                lformal.meet(lactual);
                if(lformal.hasChanged()){
                    this.addToQueue = true;
                    lformal.updatePrev();
                }
            }
            else if(actual instanceof MemberField){
                lformal = n.pts.get(n.fields.get(i));
                Lattice lthis = pts.get(fMap.get("this"));
                Lattice lthis_cp = lthis.copy();
                
                for(Reference rt : lthis_cp.curr){
                    lactual = rt.getLattice(actual);
                    // System.out.println(lformal.equals(lactual));
                    // System.out.println("Modifyme");
                    // update for all possible parameters 
                    lformal.meet(lactual);
                    if(lformal.hasChanged()){
                        this.addToQueue = true;
                        lformal.updatePrev();
                    }
                }
            }
            else if(actual instanceof BasicField){
                // do nothing
            }
        }

        /**
         * Assign return value of function to v
         */
        void assignReturn(){

            Lattice lv = null;
            Lattice lret = n.pts.get(n.ret_field);
            if(v instanceof LocalField) {
                lv = pts.get(v);

                // Take meet with lret
                lv.meet(lret);
            } else 
            if(v instanceof MemberField){
                Lattice lthis = pts.get(fMap.get("this"));
                Lattice lthis_cp = lthis.copy();
                for(Reference r : lthis_cp.curr){
                    lv = r.getLattice(v);

                    lv.meet(lret);
                }
            }
            else{
                // do nothing
            }
        }

        @Override
        void analyzeStatement() {
            
            this.addToQueue = false;
            // Do formals = actuals for function
            
            this.assignFormal(0, w);
            for(int i=0;i<args.size();++i){
                this.assignFormal(i+1, args.get(i));
            }

            // Do v = return_val
            this.assignReturn();

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(v instanceof MemberField)sb.append("this.");
            sb.append(v.name + " = ");
            if(w instanceof MemberField)sb.append("this.");
            sb.append(w.name+"."+n.fname+"(");
            String pre="";
            for(Field a : args){
                sb.append(pre);
                pre=",";
                if(a instanceof MemberField || a instanceof BasicMemberField)
                    sb.append("this.");
                sb.append(a.name);
            }
            sb.append(") ;    [ class "+n.cname+" ]");
            return sb.toString();
        }

    }

    String fname;   
    String cname;
    ClassInfo ci;                               // Class of current function
    
    String ret_val;
    Field ret_field;                            // Return type - either basic or local or ref
    String ret_type;
    List<Field> formals;                        // Formal params of current function

    List<Field> fields;                         // All local fields
    Map<String, Field> fMap;                    // Field name to field
    List<Mod>  flow;                            // statements affecting rho.
    List<CallLabel> adj;                        // set of function calls 
    
    Map<Field, Lattice> pts;                    // points to set for fn. 
    Map<Reference, Map<Field, Lattice>> hpts;   // heap
    boolean summaryChange;                      // summary of the function
    boolean retChange;                          // return value of function
    boolean heapChange;                         // heap of the function
    boolean isMain;                             // is main()?                             

    Set<Function> callSet;                      // set of all functions called by this
    
    /**
     * Default constructor
     * @param fname
     * @param cname
     * @param ci
     */
    public Function(String fname, String cname, ClassInfo ci) {

        this.fname=fname;
        this.cname=cname;
        this.ci = ci;

        this.ret_val = "";
        this.ret_field = null;
        this.ret_type = "";
        this.formals = new ArrayList<>();

        this.fields = new ArrayList<>();
        this.flow = new ArrayList<>();
        this.adj = new ArrayList<>();
        this.fMap = new HashMap<>();
        this.pts = new HashMap<>();
        this.callSet = new HashSet<>();
        this.hpts = null;
        this.summaryChange = false;
        this.isMain = false;
    }  

    /**
     * Sets function as main function of main class
     */
    void setMain(){
        this.isMain = true;
    }

    /**
     * Add local field to Function
     * @param t Field
     */
    void addField(Field t) {
        fields.add(t);
        fMap.put(t.name, t);
    }

    /**
     * Returns the appropriate field object of the current string x
     * @param x
     * @return {@code Field} corresponding to x
     */
    Field getField(String x){
        Field t = null;
        if(fMap.containsKey(x)){
            // local field - either basic or func local
            t = fMap.get(x);
        } else {
            // member variable
            for(Field f : this.ci.fields){
                if(x.equals(f.name)){
                    t = f;
                    break;
                }
            }
        }
        return t;
    }

    /**
     * Store copy of heap in function
     * @param hpts
     */
    void setHeap(Map<Reference, Map<Field, Lattice>> hpts){
        this.hpts = hpts;
    }

    /**
     * Function call
     * @param op    Operation performed
     * @param v     The result variable
     * @param w    Calling object
     * @param n    Function list(Afer CHA)
     * @param args Function call args
     */
    void addStatement(Operations op, Field v, Field w, List<Function> n, List<Field> args){
        for(Function n1 : n) {
            Mod stmt = null;
            this.callSet.add(n1);
            if(v instanceof LocalField) {
                if(w instanceof LocalField){
                    // v = w.n(...)
                    stmt = new CallLabel(v,Operations.ASSIGN, w, Operations.ASSIGN, n1, args);
                }
                else{
                    // v = this.w.n(...)
                    stmt = new CallLabel(v, Operations.ASSIGN, w, Operations.LOAD, n1, args);
                }
            }
            else {
                if( w instanceof LocalField){
                    // this.v = w.n(...)
                    stmt = new CallLabel(v, Operations.STORE, w, Operations.ASSIGN, n1, args);
                }
                else {
                    // this.v = this.w.n(...)
                    stmt = new CallLabel(v, Operations.STORE, w, Operations.LOAD, n1, args);
                }
            }
            flow.add(stmt);
        }
    }

    /**
     * Load, Store or Basic Binary Operation
     * @param op Operation performed
     * @param v Result variable
     * @param w Operand 1
     * @param f Operand 2
     */
    void addStatement(Operations op, Field v, Field w, Field f) {
        
        Mod stmt = null;
        switch(op){
            case LOAD:
                if(v instanceof LocalField) {
                    stmt = new Load(v, w, f, op);
                }
                else if(v instanceof MemberField){
                    stmt = new DoubleAssign(Operations.DOUBLEASSIGN, this.fMap.get("this"), w, v, f);
                }
                else {
                    stmt = new BasicBinary(v, op, w, f);
                }
                break;

            case STORE:
                // v.w = f
                if(f instanceof LocalField) {
                    stmt = new Store(v, f, w, op);
                }
                else if(f instanceof MemberField){
                    stmt = new DoubleAssign(Operations.DOUBLEASSIGN, v, this.fMap.get("this"), w, f);
                }
                else {
                    stmt = new BasicBinary(v, op, w, f);
                }
                break;
        
            case AND:
            case LEQ:
            case ADD:
            case SUB:
            case MULT:
            case ARRAYLOOKUP:
            case ARRAYASSIGN:
                stmt = new BasicBinary(v, op, w, f);
                break;

            default:
                break;
        }

        flow.add(stmt);
    }

    /**
     * Assignment or Unary operation
     * @param op
     * @param v
     * @param w
     */
	void addStatement(Operations op, Field v, Field w) {
        Mod stmt = null;
        
        switch(op) {
            case ARRAYLENGTH:
            case ASSIGNCONST:
            case NOT:
                stmt = new BasicUnary(v, op, w);
                break;
            case ASSIGN:
                if(v instanceof LocalField){
                    if(w instanceof LocalField){
                        stmt = new Assign(v, w, op);
                    }
                    else if(w instanceof MemberField){
                        stmt = new Load(v,this.fMap.get("this"),w,op);
                    } 
                } 
                else if(v instanceof MemberField) {
                    if(w instanceof LocalField){
                        stmt = new Store(this.fMap.get("this"), w, v, op);
                    }
                    else if(w instanceof MemberField){
                        // Handle implicit this.v = this.w
                        stmt = new DoubleAssign(Operations.DOUBLEASSIGN, this.fMap.get("this"), this.fMap.get("this"), v, w);
                    }
                } 
                else{
                    // Both should be basic members
                    stmt = new BasicUnary(v, op, w);
                }
                break;
            default:
                break;
        }

        flow.add(stmt);
	}

    /**
     * Allocation operation
     * @param op
     * @param v
     * @param r
     */
	void addStatement(Operations op, Field v, Reference r) {
        // Handle  implicit this.v = new T() 
        Mod stmt = new Allocate(v, op, r);
        flow.add(stmt);
	}

    /**
     * Initializes points to set for all function locals in stack
     * @param refList List of all references
     */
    void buildLattice(List<Reference> refList) {
        for(Field f : this.fields) {
            // Only local fields given a lattice value
            if(f instanceof LocalField){
                Lattice fv = new Lattice(refList);
                this.pts.put(f, fv);
            }
        }
    }

    /**
     * Print stack variables
     */
    void printStack() {
        String sp = "\t";
        for(Field f : this.pts.keySet()) {
            System.out.println(sp + f.name+" -> {" + this.pts.get(f) + " }" );
        }
    }

    /**
     * Check if current function calls function f
     * @param f
     * @return
     */
    boolean calls(Function f) {
        boolean isCalled = false;
        isCalled = this.callSet.contains(f);
        return isCalled;
    }

    /**
     * Updates flag if return value has changed
     */
    void updateChangeReturn(){

        // Check lattice value for return
        this.retChange = false;
        if(this.ret_field instanceof LocalField){
            if(this.pts.get(this.ret_field).hasChanged()){
                this.retChange = true;
            }
            this.pts.get(this.ret_field).updatePrev();
        } else if(this.ret_field instanceof MemberField) {
            // return this.ret_field
            Field thisField = this.getField("this") ;
            
            for(Reference r : this.pts.get(thisField).curr){
                
                Field f = r.getField(this.ret_field);

                if(this.hpts.get(r).get(f).hasChanged()){
                    this.retChange = true;
                }
                this.hpts.get(r).get(f).updatePrev();
            }
        } else{
            // ret_val is a basic field
            this.retChange = false;
        }
    }

    /**
     * Updates flag if heap has changed
     */
    void updateChangeHeap(){
        this.heapChange = false;
        for(Reference r : this.hpts.keySet()){
            Map<Field, Lattice> rMap = this.hpts.get(r);
            for(Field f : rMap.keySet()){
                // Check if element has changed
                if(rMap.get(f).hasChanged()){
                    this.heapChange = true;
                }
                rMap.get(f).updatePrev();
            }
        }
    }
    
    /**
     * Updates if the summary of the function after one run has changed or not.
     * Considered as change in return value + change in heap value
     */
    void updateSummary() {
        if(!this.isMain){
            this.updateChangeReturn();
        }
        this.updateChangeHeap();
        this.summaryChange = this.retChange | this.heapChange;
    }

    /**
     * Perform intraprocedural + interprocedural PTA
     * @return Queue of next functions to be processed
     */
    Queue<Function> analyze(){
        Queue<Function> res = new LinkedList<>();
        
        // Analyze all statements and update the lattice values
        for(Mod stmt : this.flow) {
            stmt.analyzeStatement();
            if(stmt instanceof CallLabel){
                CallLabel fcall = (CallLabel)stmt;
                if(fcall.addToQueue == true){
                    res.add(fcall.n);
                }
                // Don't add multiple times
                fcall.addToQueue = false;
            }
        }

        return res;
    }

    /**
     * Set the return value of function
     * @param ret_val
     */
    void setReturnName(String ret_val) {
        this.ret_val = ret_val;
    }

    /**
     * Set ret_field
     */
    void setReturnField() {
        this.ret_field = this.getField(this.ret_val);
        // System.out.println(this.fname);
        // System.out.println(this.ret_val);
        this.ret_type = this.ret_field.type; 
    }

    /**
     * Reset all change flags
     */
    void resetChangeFlags() {
        this.retChange = false;
        this.heapChange = false;
        this.summaryChange = false;
    }

    /**
     * Add formal parameter to current function
     * @param t
     */
    public void addFormalField(Field t) {
        this.formals.add(t);
        this.addField(t);   
    }

    /**
     * Print all statements in current function
     */
    public void printStatements() {
        System.out.println("    "+this.fname);
        int c=1;
        for(Mod i : this.flow){
            System.out.println((c++) + "      "+i);
        }
    }
}