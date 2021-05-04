package tools;

import java.util.ArrayList;
import java.util.List;

public class Klass {
    String cname;
    boolean isThread;
    List<Field> cFields;
    List<Field> lFields;

    public Klass(String cname, boolean isThread) {
        this.cname = cname;
        this.isThread = isThread; 
        this.cFields = new ArrayList<>();
        this.lFields = new ArrayList<>(); 
    }

    public Field getField(String var) {
        Field key=null;
        
        for(Field f : this.cFields){
            if(f.name.equals(var)){
                key=f;
                break;
            }
        }

        for(Field f : this.lFields){
            if(f.name.equals(var)){
                key=f;
                break;
            }
        }

        return key;
    }
}
