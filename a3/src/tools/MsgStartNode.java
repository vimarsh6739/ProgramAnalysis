package tools;


public class MsgStartNode extends BB {
    Field tField;

    public MsgStartNode(NodeType op, int bbid,int tid, String ann, Field tField) {
        super(op,bbid,tid,ann);
        this.tField=tField;
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
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t");
        
        sb.append(tField.name+".start()\n");

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
