package tools;

import java.util.ArrayList;

public class SimpleStmt extends BB {
    
    // Atmost 3 operands(TAC)
    Field op1,op2,op3;

    public SimpleStmt(Operations op, int bbid, Field op1) {
        super(op, bbid);
        this.op1 = op1;
    }

    public SimpleStmt(Operations op, int bbid, Field op1, Field op2) {
        this(op,bbid,op1);
        this.op2=op2;
    }

    public SimpleStmt(Operations op, int bbid, Field op1, Field op2, Field op3){
        this(op, bbid, op1, op2);
        this.op3=op3;
    }

    public SimpleStmt(Operations op, int bbid, String ann, Field op1) {
        super(op, bbid,ann);
        this.op1 = op1;
    }

    public SimpleStmt(Operations op, int bbid, String ann, Field op1, Field op2) {
        this(op,bbid,ann,op1);
        this.op2=op2;
    }

    public SimpleStmt(Operations op, int bbid, String ann, Field op1, Field op2, Field op3){
        this(op, bbid, ann, op1, op2);
        this.op3=op3;
    }

    public SimpleStmt(SimpleStmt other, int tid) {
        this(other.op, other.bbid, other.ann, other.op1, other.op2,other.op3);
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
        this.tid = tid;
    }

    @Override
    BB copy(int tid){return new SimpleStmt(this, tid);}
}
