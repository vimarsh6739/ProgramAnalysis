package tools;

public class RefField extends MemberField {
    Reference owner;    // ownership info

    public RefField(String type, String name, String cname, Reference owner) {
        super(type, name, cname);
        this.owner = owner;
    }
}
