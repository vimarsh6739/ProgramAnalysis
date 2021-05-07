package tools;

/**
 * Wrapper class for int and boolean fields
 */
public class BasicField extends Field {
    
    /**
     * Default constructor
     * @param type
     * @param name
     */
    public BasicField(String type, String name){
        super(type, name);
    }

    /**
     * Copy constructor
     * @param other
     */
    public BasicField(BasicField other) {
        this(other.type, other.name);
    }

    /**
     * Non-typecasted deep copy
     */
    @Override
    public Field copy(){
        return new BasicField(this);
    }
}
