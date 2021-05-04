package tools;

public class SimpleStmtNode extends BB {
    
    // Atmost 3 operands(TAC)
    Field op1,op2,op3;

    public SimpleStmtNode(NodeType op, int bbid,int tid, Field op1) {
        super(op, bbid,tid);
        this.op1 = op1;
    }

    public SimpleStmtNode(NodeType op, int bbid,int tid, Field op1, Field op2) {
        this(op,bbid,tid,op1);
        this.op2=op2;
    }

    public SimpleStmtNode(NodeType op, int bbid,int tid, Field op1, Field op2, Field op3){
        this(op, bbid,tid, op1, op2);
        this.op3=op3;
    }

    public SimpleStmtNode(NodeType op, int bbid,int tid, String ann, Field op1) {
        super(op, bbid,tid,ann);
        this.op1 = op1;
    }

    public SimpleStmtNode(NodeType op, int bbid,int tid, String ann, Field op1, Field op2) {
        this(op,bbid,tid,ann,op1);
        this.op2=op2;
    }

    public SimpleStmtNode(NodeType op, int bbid,int tid, String ann, Field op1, Field op2, Field op3){
        this(op, bbid,tid, ann, op1, op2);
        this.op3=op3;
    }

}
