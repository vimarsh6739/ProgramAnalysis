package tools;

import java.util.ArrayList;

public class StartStmt extends BB {
    Field buffer;

    public StartStmt(Operations op, int bbid, Field buffer) {
        super(op, bbid);
        this.buffer=buffer;
    }

    public StartStmt(Operations op, int bbid, String ann, Field buffer) {
        super(op,bbid,ann);
        this.buffer=buffer;
    }

    public StartStmt(StartStmt other,int tid) {
        this(other.op, other.bbid, other.ann, other.buffer);
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.tid=tid;
    }

    @Override
    BB copy(int tid){return new StartStmt(this,tid);}
}
