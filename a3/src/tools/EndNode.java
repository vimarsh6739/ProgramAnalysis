package tools;

public class EndNode extends BB {
    public EndNode(NodeType op,int bbid,int tid) {
        super(op, bbid, tid, null);
    }

    @Override
    public void updateInEdge(BB parent) {
        this.localPred.addAll(parent.flowInfo);
    }
    
    @Override
    public void updateSummary() {
        this.flowInfo.add(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("BB"+bbid+":\t");
        
        sb.append("END\n");
            
        sb.append("Local Pred edges = [");
        String delim = "";
        for(BB f : this.localPred){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nLocal Succ edges = [");
        delim = "";
        for(BB f : this.localSucc){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nGEN = [");
        delim = "";
        for(BB f : this.GEN){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nKILL = [");
        delim = "";
        for(BB f : this.KILL){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nM = [");
        delim = "";
        for(BB f : this.M){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nOUT = [");
        delim = "";
        for(BB f : this.OUT){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");
        
        return sb.toString();
    }
}
