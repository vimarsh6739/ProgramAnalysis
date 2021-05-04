package tools;

public class MsgNotifyNode extends BB{
    Field buffer;

    public MsgNotifyNode(NodeType op, int bbid,int tid, Field buffer) {
        super(op, bbid,tid);
        this.buffer=buffer;
    }

    public MsgNotifyNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
    }

}
