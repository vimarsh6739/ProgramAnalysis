package tools;

import java.util.*;

public class CGBuilder {

    List<ClassInfo> clist;                          // Class list
    Map<String, ClassInfo> cMap;                    // Index class map
    ClassInfo curr_class;
    
    List<Function> flist;                           // Function list
    Function curr_fn;

    List<String> refType;                           // Type of reference var.
    
    Map<Reference, Map<Field, Lattice >> sigma;

    public CGBuilder() {
        clist = new ArrayList<>();
        cMap = new HashMap<>();
        flist = new ArrayList<>();
        refType = new ArrayList<>();
        sigma = new HashMap<Reference, Map<Field, Lattice>>();
    }

    public void addClass(String cname, String pname){
        curr_class = new ClassInfo(cname, pname);
        clist.add(curr_class);
        cMap.put(cname, curr_class);
    }

    public void addMethod(String fname){
        curr_fn = new Function(fname, curr_class.cname);
        flist.add(curr_fn);                 // global fn list
        curr_class.addMethod(curr_fn);      // local fn list
    }

    public void addClassField(String type, String name){
        Field t = new MemberField(type, name, curr_class.cname);
        curr_class.addField(t);
    }

    public void addLocalField(String type, String name){
        Field t;
        switch(type){
            case "int":
            case "boolean":
                t = new BasicField(type, name);
            break;
            default:
                t = new LocalField(type, name, curr_fn.fname);
        }
        curr_fn.addField(t);
    }

    public void addReference(String cname) {
        // We initialize later as clist might be incomplete at this point
        refType.add(cname);
    }
    // 2. Inheritance relations updated 

    // 3. Iterative worklist algo
}