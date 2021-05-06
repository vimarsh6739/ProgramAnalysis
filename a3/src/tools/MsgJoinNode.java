package tools;


public class MsgJoinNode extends BB{
    Field tField;

    public MsgJoinNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.tField=buffer;
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
    public void initializeGenKill() {
        // N(tField) to kill set
        int other_tid = st.thFieldMap.get(this.tField);
        this.KILL.addAll(st.N.get(other_tid));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t");
        
        sb.append(tField.name+".join()\n");

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
        
        return sb.toString();
    }
}
