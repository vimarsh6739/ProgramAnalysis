package tools;

public class MsgNotifyNode extends BB{
    Field buffer;

    public MsgNotifyNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
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
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t");
        
        sb.append(buffer.name+".");
        if(op == NodeType.NOTIFY)sb.append("notify()\n");
        else sb.append("notifyAll()\n");

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
