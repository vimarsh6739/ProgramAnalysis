package tools;

public class NotifiedEntryNode extends BB {
    Field buffer;
    public NotifiedEntryNode(int bbid,int tid,Field buffer) {
        super(NodeType.NOTIFIED_ENTRY,bbid,tid,null);
        this.buffer=buffer;
    }
}
