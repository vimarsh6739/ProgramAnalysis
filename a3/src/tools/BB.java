package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic Basic Block(part of both the CFG and PEG)
 */
public class BB {
    List<BB> inEdges;
    List<BB> outEdges;
    Operations op;
    int bbid;           // global id of current basic block
    String ann;         // user annotation 
    int tid;            // ThreadId(in PEG)

    public BB() {
        op=Operations.NOP;
        inEdges=null;
        outEdges=null;
        ann=null;
        bbid=-1;
        tid=-1;
    }

    public BB(Operations op, int bbid) {
        this.op = op;
        this.bbid = bbid;
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        tid=-1;
    }

    public BB(Operations op, int bbid, String ann){
        this(op, bbid);
        this.ann = ann;
    }

    public BB(BB s,int tid) {
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.op     = s.op;
        this.bbid   = s.bbid;
        this.ann    = s.ann;
        this.tid    = tid;
    }

    BB copy(int tid){return new BB(this,tid);}
}
