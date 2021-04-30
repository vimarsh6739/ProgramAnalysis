package tools;

import java.util.ArrayList;

public class IfElseBlock extends BB {
    BB bif;
    BB belse;
    Field cond;
    
    public IfElseBlock(Operations op,int bbid,Field cond) {
        super(op, bbid);
        bif=null;
        belse=null;
        this.cond=cond;
    }   
    
    public IfElseBlock(Operations op, int bbid, String ann,Field cond){
        super(op,bbid,ann);
        bif=null;
        belse=null;  
        this.cond=cond;
    }
    
    public IfElseBlock(IfElseBlock s,int tid) {
        this(s.op,s.bbid,s.ann,s.cond);
        this.tid   = tid;
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.bif = s.bif.copy(tid);
        this.belse = s.belse.copy(tid);
    }
    
    @Override
    BB copy(int tid){return new IfElseBlock(this,tid); }
}
