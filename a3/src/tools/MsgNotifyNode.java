package tools;

public class MsgNotifyNode extends BB{
    Field buffer;

    public MsgNotifyNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
    }
}
