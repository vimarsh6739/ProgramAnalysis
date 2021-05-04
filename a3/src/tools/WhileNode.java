package tools;

public class WhileNode extends BB {
    Field cond;
    BB body;

    public WhileNode(NodeType op, int bbid,int tid, String ann, Field cond) {
        super(op,bbid,tid,ann);
        this.cond=cond;
        this.body=null;
    }

    @Override
    public void addNode(BB blk) {
        this.body = blk;
    }
}
