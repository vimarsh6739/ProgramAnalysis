package tools;
import java.util.*;
import java.util.stream.Collectors;

public class Function {

    class Mod {
        Field x;  
        Operations op;
        
        public Mod(Field x, Operations op) {
            this.x = x;
            this.op = op;
        }
    }

    class Assign extends Mod {
        Field y;

        public Assign(Field x, Field y, Operations op) {
            super(x, op);
            this.y=y;
        }
    }
    
    class Load extends Mod {        
        Field y;
        Field fy;                       // x = y.f

        public Load(Field x, Field y, Field fy, Operations op) {
            super(x, op);
            this.fy=fy;
            this.y=y;
        }
    }

    class Store extends Mod {
        Field fx;                       // x.f = y
        Field y;

        public Store(Field x, Field y, Field fx, Operations op) {
            super(x, op);
            this.fx=fx;
            this.y=y;
        }
    }

    class Allocate extends Mod {        
        Reference r;
        
        public Allocate(Field x, Operations op, Reference r) {
            super(x, op);
            this.r=r;
        }
    }

    class BasicBinary extends Mod {                      
        Field y;
        Field z;

        public BasicBinary(Field x, Operations op, Field y, Field z) {
            super(x, op)    ;
            this.y=y;
            this.z=z;
        }
    }

    class BasicUnary extends Mod {   
        Field y;

        public BasicUnary(Field x, Operations op, Field y) {
            super(x, op);
            this.y=y;
        }
    }

    class CallLabel extends Mod {
        Field y;
        Function foo;                               
        List<Field> args;

        public CallLabel(Field x, Operations op, Field y, Function foo, List<Field> args) {
            super(x, op);
            this.y=y;
            this.foo = foo;
            this.args = new ArrayList<>(args);
        }
    }

    String fname;   
    String cname;
    ClassInfo ci;                       // Class of current function
    Field ret;                          // Return type - either basic, or local, or ref

    List<Field> fields;                 // All local fields
    Map<String, Field> fMap;            // Field name to field
    List<Mod>  flow;                    // statements affecting rho.
    List<CallLabel> adj;                // set of function calls 
    
    Map<Field, Lattice> stack;            // function stack 
    
    public Function(String fname, String cname, ClassInfo ci) {
        this.fname=fname;
        this.cname=cname;
        this.ci = ci;
        this.ret = null;

        this.fields = new ArrayList<>();
        this.flow = new ArrayList<>();
        this.adj = new ArrayList<>();
        this.fMap = new HashMap<>();
        this.stack = new HashMap<>();
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
     * Function call
     * @param op    Operation performed
     * @param x     The result variable
     * @param e1    Calling object
     * @param e2    Function name
     * @param eargs Function call args
     */
    void addStatement(Operations op, String fx, String e1, Function f, List<String> sargs){
        
        // Statement to be added
        Field x = this.getField(fx);
        Field o = this.getField(e1);               
        
        List<Field> args = sargs.stream()
                                  .map(elt -> getField(elt))
                                  .collect(Collectors.toList());
        
        Mod stmt = new CallLabel(x, op, o, f, args);
        flow.add(stmt);
    }

    /**
     * Load, Store or Basic Binary Operation
     * @param op
     * @param fx    
     * @param e1
     * @param e2
     */
    public void addStatement(Operations op, String fx, String e1, String e2) {
        Mod stmt = null;
        Field x  = this.getField(fx);
        Field fe1 = this.getField(e1);
        Field fe2 = this.getField(e2);

        switch(op){
            case LOAD:
                stmt = new Load(x, fe1, fe2, op);
                break;

            case STORE:
                stmt = new Store(x, fe1, fe2, op);
                break;
        
            case AND:
            case LEQ:
            case ADD:
            case SUB:
            case MULT:
            case ARRAYLOOKUP:
            case ARRAYASSIGN:
                stmt = new BasicBinary(x, op, fe1, fe2);
                break;

            default:
                break;
        }

        flow.add(stmt);
    }

    /**
     * Assignment or Unary operation
     * @param op
     * @param fx
     * @param e1
     */
	public void addStatement(Operations op, String fx, String e1) {
        Field x = this.getField(fx);
        Field y = this.getField(e1);    // null for constants
        Mod stmt = null;

        switch(op){
            case ARRAYLENGTH:
            case ASSIGNCONST:
            case NOT:
                stmt = new BasicUnary(x, op, y);
                break;
            case ASSIGN:
                stmt = new Assign(x, y, op);
                break;
            default:
                break;
        }

        flow.add(stmt);
	}

    /**
     * Allocation operation
     * @param op
     * @param fx
     * @param r
     */
	public void addStatement(Operations op, String fx, Reference r) {
        
        Field x = this.getField(fx);
        Mod stmt = new Allocate(x, op, r);
        flow.add(stmt);
	}

    /**
     * Initializes points to set for all function locals in stack
     * @param refList List of all references
     */
    public void buildLattice(List<Reference> refList) {
        for(Field f : this.fields) {
            // Only local fields given a lattice value
            if(f instanceof LocalField){
                Lattice fv = new Lattice(refList);
                this.stack.put(f, fv);
            }
        }
    }

    /**
     * Print stack variables
     */
    public void printStack() {
        for(Field f : this.stack.keySet()){
            System.out.print("    " + "("+f.name+" : " + f.type + ")");
            System.out.println(" -> { " + this.stack.get(f).printValue() + " }" );
        }
    }
}