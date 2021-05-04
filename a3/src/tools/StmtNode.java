package tools;

public class StmtNode extends BB {
    
    Field x,y,z;

    public StmtNode(NodeType op,int bbid,int tid,String ann,Field x,Field y,Field z) {
        super(op,bbid,tid,ann);
        this.x=x;
        this.y=y;
        this.z=z;
    }
}
