package tools;

public class BasicMemberField extends BasicField {
    String cname;

    /**
     * Default Constructor
     * @param type
     * @param name
     * @param cname
     */
    public BasicMemberField(String type, String name, String cname) {
        super(type, name);
        this.cname = cname;
    }

    /**
     * Copy Constructor
     * @param other
     */
    public BasicMemberField(BasicMemberField other) {
        this(other.type, other.name, other.cname);
    }

    /**
     * Non-typecasted deep copy
     */
    @Override
    public Field copy(){
        return new BasicMemberField(this);
    }
}
