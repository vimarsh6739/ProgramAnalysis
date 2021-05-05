package tools;

public class EndNode extends BB {
    public EndNode(NodeType op,int bbid,int tid) {
        super(op, bbid, tid, null);
    }

    @Override
    public void updateInEdge(BB parent) {
        this.inEdges.addAll(parent.flowInfo);
    }
    
    @Override
    public void updateSummary() {
        this.flowInfo.add(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("BB"+bbid+": (method end)\t");
        
        sb.append("End\n");
            
        sb.append("In edges = [");
        String delim = "";
        for(BB f : this.inEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nOut edges = [");
        delim = "";
        for(BB f : this.outEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nCross edges = [");
        for(BB f : this.crossEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");
        
        return sb.toString();
    }
}
