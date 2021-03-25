package tools;

public class RefField extends MemberField {
    Reference owner;    // ownership info

    /**
     * Default constructor
     * @param type
     * @param name
     * @param cname
     * @param owner
     */
    public RefField(String type, String name, String cname, Reference owner) {
        super(type, name, cname);
        this.owner = owner;
    }

    /**
     * Copy constructor
     * @param other
     */
    public RefField(RefField other) {
        this(other.type, other.name, other.cname, other.owner);
    }

    /**
     * Non-typecasted deep copy
     */
    @Override
    public Field copy() {
        return new RefField(this);
    }
}
