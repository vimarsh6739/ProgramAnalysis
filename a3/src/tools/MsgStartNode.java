package tools;


public class MsgStartNode extends BB {
    Field tField;

    public MsgStartNode(NodeType op, int bbid,int tid, String ann, Field tField) {
        super(op,bbid,tid,ann);
        this.tField=tField;
    }
}
