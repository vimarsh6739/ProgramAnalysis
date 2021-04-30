package tools;

import java.util.ArrayList;

public class JoinStmt extends BB{
    Field buffer;

    public JoinStmt(Operations op, int bbid, Field buffer) {
        super(op, bbid);
        this.buffer=buffer;
    }

    public JoinStmt(Operations op, int bbid, String ann, Field buffer) {
        super(op,bbid,ann);
        this.buffer=buffer;
    }

    public JoinStmt(JoinStmt other,int tid) {
        this(other.op, other.bbid, other.ann, other.buffer);
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.tid=tid;
    }

    @Override
    BB copy(int tid){return new JoinStmt(this,tid);}
}
