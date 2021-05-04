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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": (if-else block)\t");
        
        sb.append(st.nestIndent+"IF("+cond.name+")\n");
        st.nestIndent += "\t";
        sb.append(ifNode.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        
        sb.append(st.nestIndent+"ELSE\n");
        st.nestIndent += "\t";
        sb.append(elseNode.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        sb.append(st.nestIndent+"ENDIF ->BB"+bbid+": (if-else block end)\n\n");
        return sb.toString();
    }
}
