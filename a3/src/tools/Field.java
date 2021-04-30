package tools;

import java.util.ArrayList;
import java.util.List;

public class Field {
    String type;
    String id;
    List<Klass> scope;

    public Field() {
        type=id=null;
        scope = new ArrayList<>();
    }
    
    public Field(Field f) {
        this.type = f.type;
        this.id = f.id;
        this.scope = new ArrayList<>(f.scope);
    }

    public Field(String type, String id){
        this.type = type;
        this.id = id;
        this.scope = new ArrayList<>();
	}

	Field copy(){
        return new Field(this);
    }
}
