package tools;


public class MsgJoinNode extends BB{
    Field tField;

    public MsgJoinNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.tField=buffer;
    }
}
