package tools;

import java.util.ArrayList;

public class SyncBlock extends BB{
    Field buffer;
    BB body;

    public SyncBlock(Operations op, int bbid, Field buffer) {
        super(op, bbid);
        this.buffer=buffer;
        this.body=null;
    }

    public SyncBlock(Operations op, int bbid, String ann, Field buffer) {
        super(op,bbid,ann);
        this.buffer=buffer;
        this.body=null;
    }

    public SyncBlock(SyncBlock other,int tid) {
        this(other.op, other.bbid, other.ann, other.buffer);
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.tid=tid;
        this.body = other.body.copy(tid);
    }

    @Override
    BB copy(int tid){return new SyncBlock(this,tid);}
}
