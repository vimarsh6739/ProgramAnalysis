package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reference {
    String cname;
    List<Field> mvars;          // Contains only RefField entries 
    Map<Field, Lattice> refMap; // Subset of sigma containg current values
    public Reference(ClassInfo ci) {
        this.cname=ci.cname;
        mvars = new ArrayList<>();
        refMap = new HashMap<>();
        // Create new Field object for every member variable
        for(Field f : ci.fields){
            mvars.add(new RefField(f.type, f.name, ((MemberField)f).cname, this));
        }
    }
}
