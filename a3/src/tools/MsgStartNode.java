package tools;


public class MsgStartNode extends BB {
    Field tField;

    public MsgStartNode(NodeType op, int bbid,int tid, Field buffer) {
        super(op, bbid,tid);
        this.tField=buffer;
    }

    public MsgStartNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.tField=buffer;
    }
}
