package tools;
import java.util.ArrayList;

public class WaitStmt extends BB {
    Field buffer;
    BB waiting_pred;
    BB notified_entry;

    public WaitStmt(Operations op, int bbid, Field buffer) {
        super(op, bbid);
        this.buffer=buffer;
        this.waiting_pred=null;
        this.notified_entry=null;
    }

    public WaitStmt(Operations op, int bbid, String ann, Field buffer) {
        super(op,bbid,ann);
        this.buffer=buffer;
        this.waiting_pred=null;
        this.notified_entry=null;
    }

    public WaitStmt(WaitStmt other,int tid) {
        this(other.op, other.bbid, other.ann, other.buffer);
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.tid=tid;
        this.waiting_pred=new WaitingPred(tid);
        this.notified_entry=new NotifiedEntry(tid);
    }

    @Override
    BB copy(int tid){return new WaitStmt(this,tid);}
}
