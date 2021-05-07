package tools;

public class MemberField extends Field{
    String cname;
    
    /**
     * Constructor for class field
     * @param type  Field type
     * @param name  Field id
     * @param cname Base class in which field is defined
     */
    public MemberField(String type, String name, String cname) {
        super(type, name);
        this.cname = cname;
    }

    /**
     * Copy constructor
     * @param t Field to copy
     */
    public MemberField(MemberField t) {
        this(t.type, t.name, t.cname);
    }

    @Override
    public Field copy(){
        return new MemberField(this);
    }
}
