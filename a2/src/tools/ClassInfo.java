package tools;

import java.util.ArrayList;
import java.util.List;

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

    void addField(Field f){fields.add(f);}
}
