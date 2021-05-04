package tools;

public class SyncBlockNode extends BB{
    Field buffer;
    BB body;

    public SyncBlockNode(NodeType op, int bbid,int tid, Field buffer) {
        super(op, bbid,tid);
        this.buffer=buffer;
        this.body=null;
    }

    public SyncBlockNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
        this.body=null;
    }
}
