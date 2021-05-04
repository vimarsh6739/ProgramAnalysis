package tools;

public class ExitNode extends BB {
    Field buffer;

    public ExitNode(int bbid,int tid,String ann,Field buffer) {
        super(NodeType.ENTRY,bbid,tid,ann);
        this.buffer=buffer;
    }
}
