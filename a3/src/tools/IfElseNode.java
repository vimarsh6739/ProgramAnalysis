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
    public void updateInEdge(BB parent) {
        this.inEdges.addAll(parent.flowInfo);
        this.flowInfo.add(this);

        // Perform DFS on ifNode
        this.ifNode.updateInEdge(this);
        this.ifNode.updateSummary();

        // Perform DFS on elseNode
        this.elseNode.updateInEdge(this);
        this.elseNode.updateSummary();
    }

    @Override
    public void updateSummary() {
        // Remove self entry before updating flowEntries
        this.flowInfo.remove(this);

        // It may be readded in case of empty blocks
        this.flowInfo.addAll(this.ifNode.flowInfo);
        this.flowInfo.addAll(this.elseNode.flowInfo);
    }

    @Override
    public void updateOutEdge() {
        for(BB f : this.inEdges){
            f.outEdges.add(this);
        }

        this.ifNode.updateOutEdge();
        this.elseNode.updateOutEdge();
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
