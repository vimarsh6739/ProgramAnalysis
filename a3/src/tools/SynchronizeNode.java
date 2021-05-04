package tools;

public class SynchronizeNode extends BB{
    Field buffer;
    BB entry;
    BB body;
    BB exit;
                                                                                
    public SynchronizeNode(NodeType op, int bbid,int tid, String ann, Field buffer, BB entry, BB exit) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
        this.body=entry;
        this.exit=exit;
    }

    @Override
    public void addNode(BB blk) {
        this.body=blk;
    }
}
