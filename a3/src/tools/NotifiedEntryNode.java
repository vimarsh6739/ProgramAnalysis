package tools;

public class NotifiedEntryNode extends BB {
    Field buffer;
    public NotifiedEntryNode(int bbid,int tid,Field buffer) {
        super(NodeType.NOTIFIED_ENTRY,bbid,tid,null);
        this.buffer=buffer;
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
        sb.append(st.nestIndent+"BB"+bbid+":\t");
        
        sb.append("("+buffer.name+",notified-entry)\n");

        sb.append(st.nestIndent+"In edges = [");
        String delim = "";
        for(BB f : this.inEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"Out edges = [");
        delim = "";
        for(BB f : this.outEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"Cross edges = [");
        for(BB f : this.crossEdges){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");
        
        return sb.toString();
    }
}
