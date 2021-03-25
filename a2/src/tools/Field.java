package tools;

public class Field {
    String type;
    String name;
    
    /**
     * Default constructor for a field
     * @param type  Field type
     * @param name  Field name
     */
    public Field(String type, String name){
        this.type = type;
        this.name = name;
    }

    /**
     * Copy constructor for Field
     * @param t Field to copy
     */
    public Field(Field t){
        this(t.name,t.type);
    }

    /**
     * Non-typecasted deep copy
     * @return Deep copy of self
     */
    public Field copy(){
        return new Field(this);
    }
}