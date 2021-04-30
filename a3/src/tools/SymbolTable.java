package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    
    List<Klass> cList;
    Map<String,Klass> cMap;
    
    List<Field> gfList;
    Map<String,Field> fMap;
    
    Klass curr_class;
    boolean inRun;
    
    public SymbolTable() {
        this.cList      = new ArrayList<>();
        this.cMap       = new HashMap<>();

        this.gfList     = new ArrayList<>();
        this.fMap       = new HashMap<>();

        this.curr_class = null;
        this.inRun      = false;
    }

	public void addClass(String cname, boolean isThread) {
        Klass k = new Klass(cname, isThread);
        this.cList.add(k);
        this.cMap.put(cname, k);
        this.curr_class = k;
    }

    public void addLocalField(String type, String var) {
        Field f;
        if(this.fMap.containsKey(var)){
            f = this.fMap.get(var);
        }
        else{
            f = new Field(type,var);
            this.gfList.add(f);
            this.fMap.put(var, f);
        }

        this.curr_class.lFields.add(f);
        f.scope.add(this.curr_class);             
    }

	public void addMemberField(String type, String var) {
        Field f;
        if(this.fMap.containsKey(var)){
            f = this.fMap.get(var);
        }
        else{
            f = new Field(type,var);
            this.gfList.add(f);
            this.fMap.put(var, f);
        }
        this.curr_class.cFields.add(f);
        f.scope.add(this.curr_class);
	}

	public void printVariables() {
        String ts = "\t";
        for(Klass k: this.cList){
            System.out.println("Class " + k.cname);
            System.out.println("Members:");
            for(Field f : k.cFields){
                System.out.println(ts + f.id + " : "  + f.type);
            }
            System.out.println("Locals:");
            for(Field f : k.lFields){
                System.out.println(ts + f.id + " : "  + f.type);
            }
        }
	}
    
}
