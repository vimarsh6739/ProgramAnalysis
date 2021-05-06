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
        this.localPred.addAll(parent.flowInfo);
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
        for(BB f : this.localPred){
            f.localSucc.add(this);
        }

        this.ifNode.updateOutEdge();
        this.elseNode.updateOutEdge();
    }

    @Override
    public void updateStartEdge() {
        this.ifNode.updateStartEdge();
        this.elseNode.updateStartEdge();
    }

    @Override
    public void updateMonitor(Field obj, boolean b) {
        if(b){
            st.monitor.get(obj).add(this);
        }

        this.ifNode.updateMonitor(obj, b);
        this.elseNode.updateMonitor(obj, b);
    }
    
    @Override
    public void initializeGenKill() {
        this.ifNode.initializeGenKill();
        this.elseNode.initializeGenKill();    
    }

    @Override
    public void initializeWorklist() {
        this.ifNode.initializeWorklist();
        this.elseNode.initializeWorklist();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+":\t");
        
        sb.append(st.nestIndent+"IF("+cond.name+")\n");
        sb.append(st.nestIndent+"Local Pred edges = [");
        String delim = "";
        for(BB f : this.localPred){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"Local Succ edges = [");
        delim = "";
        for(BB f : this.localSucc){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"GEN = [");
        delim = "";
        for(BB f : this.GEN){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"KILL = [");
        delim = "";
        for(BB f : this.KILL){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"M = [");
        delim = "";
        for(BB f : this.M){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"OUT = [");
        delim = "";
        for(BB f : this.OUT){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");
        
        st.nestIndent += "\t";
        sb.append(ifNode.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        
        sb.append(st.nestIndent+"ELSE\n");
        st.nestIndent += "\t";
        sb.append(elseNode.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        sb.append(st.nestIndent+"ENDIF ->BB"+bbid+":\n\n");
        return sb.toString();
    }
}
