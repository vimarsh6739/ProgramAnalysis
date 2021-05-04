package tools;

public class WhileBlockNode extends BB {
    Field cond;
    BB body;

    public WhileBlockNode(NodeType op, int bbid, int tid, Field cond) {
        super(op, bbid,tid);
        this.cond=cond;
        this.body=null;
    }

    public WhileBlockNode(NodeType op, int bbid,int tid, String ann, Field cond) {
        super(op,bbid,tid,ann);
        this.cond=cond;
        this.body=null;
    }
}
