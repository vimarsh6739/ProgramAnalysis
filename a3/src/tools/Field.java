package tools;

import java.util.ArrayList;
import java.util.List;

public class Field {
    String type;
    String name;
    List<Clazz> scope;

    public Field() {
        type=name=null;
        scope = new ArrayList<>();
    }
    
    public Field(Field f) {
        this.type = f.type;
        this.name = f.name;
        this.scope = new ArrayList<>(f.scope);
    }

    public Field(String type, String id){
        this.type = type;
        this.name = id;
        this.scope = new ArrayList<>();
	}

	Field copy(){
        return new Field(this);
    }
}
