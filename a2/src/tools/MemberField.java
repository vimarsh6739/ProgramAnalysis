package tools;

public class MemberField extends Field{
    String cname;
    public MemberField(String type, String name, String cname) {
        super(type, name);
        this.cname = cname;
    }
}
