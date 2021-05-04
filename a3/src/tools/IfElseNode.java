package tools;

public class IfElseNode extends BB {
    BB ifBranch;
    BB elseBranch;
    Field cond;
    
    public IfElseNode(NodeType op,int bbid,int tid,Field cond) {
        super(op, bbid,tid);
        ifBranch=null;
        elseBranch=null;
        this.cond=cond;
    }   
    
    public IfElseNode(NodeType op, int bbid,int tid, String ann,Field cond){
        super(op,bbid,tid,ann);
        ifBranch=null;
        elseBranch=null;  
        this.cond=cond;
    }
    
}
