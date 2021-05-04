package tools;

public class EntryNode extends BB {
    Field buffer;

    public EntryNode(int bbid,int tid,String ann,Field buffer) {
        super(NodeType.ENTRY,bbid,tid,ann);
        this.buffer=buffer;
    }
}
