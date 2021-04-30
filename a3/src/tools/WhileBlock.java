package tools;

import java.util.ArrayList;

public class WhileBlock extends BB {
    Field cond;
    BB body;

    public WhileBlock(Operations op, int bbid, Field cond) {
        super(op, bbid);
        this.cond=cond;
        this.body=null;
    }

    public WhileBlock(Operations op, int bbid, String ann, Field cond) {
        super(op,bbid,ann);
        this.cond=cond;
        this.body=null;
    }

    public WhileBlock(WhileBlock other,int tid) {
        this(other.op, other.bbid, other.ann, other.cond);
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.tid=tid;
        this.body = other.body.copy(tid);
    }

    @Override
    BB copy(int tid){return new WhileBlock(this,tid);}
}
