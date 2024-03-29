package tools;

/**
 * Fields defined within the scope of a function. 
 * By design, it's type is either a class or int[]
 */
public class LocalField extends Field {
    String fname;
    
    /**
     * Default constructor for function locals
     * @param type  Type of field
     * @param name  Identifier or {@code this}
     * @param fname Name of scoped function {@}
     */
    LocalField(String type, String name, String fname){
        super(type, name);
        this.fname = fname;
    }     
    
    /**
     * Copy constructor
     * @param other
     */
    public LocalField(LocalField other) {
        this(other.type, other.name, other.fname);
    }

    /**
     * Non-typecasted deep copy
     */
    @Override
    public Field copy() {
        return new LocalField(this) ;
    }
}
