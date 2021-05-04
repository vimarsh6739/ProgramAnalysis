package tools;

public class IfElseNode extends BB {
    BB ifNode;
    BB elseNode;
    Field cond;
    
    public IfElseNode(NodeType op, int bbid,int tid, String ann,Field cond){
        super(op,bbid,tid,ann);
        ifNode=null;
        elseNode=null;  
        this.cond=cond;
    }
    
    @Override
    public void addNode(BB blk){
        if(this.ifNode == null){
            this.ifNode = blk;
        }
        else{
            this.elseNode = blk;
        }
    }
}
