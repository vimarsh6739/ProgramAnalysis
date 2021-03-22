package tools;
import java.util.*;

public class Function {

    class Mod {
        Field x;                  
        Field y;
    }

    class Load extends Mod {        
        Field fy;                       // x = y.f
    }

    class Store extends Mod {
        Field fx;                       // x.f = y
    }
    
    class CallLabel extends Mod {
        Function foo;                   // x = y.foo(...)             
        List<Field> args;
    }

    String fname;   
    String cname;
    Field ret;                          // Return type - either basic, or local, or ref

    List<Field> fields;                 // All local fields
    List<Mod> stmts;                    // statements affecting rho.
    List<CallLabel> adj;                // set of function calls 
    
    Map<Field, Lattice> rho;            // function stack 
    
    public Function(String fname, String cname) {
        this.fname=fname;
        this.cname=cname;
        this.ret = null;

        this.fields = new ArrayList<>();
        this.stmts = new ArrayList<>();
        this.adj = new ArrayList<>();
        this.rho = new HashMap<>();
    }   

    void addField(Field t) {
        fields.add(t);
    }

}