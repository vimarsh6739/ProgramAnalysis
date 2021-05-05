package tools;

import java.util.LinkedHashSet;
import java.util.Set;

public class MsgNotifyNode extends BB{
    Field buffer;
    Set<BB> notifySucc;
    public MsgNotifyNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
        this.notifySucc = new LinkedHashSet<>();
        st.notifySucc.put(this, this.notifySucc);
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
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t");
        
        sb.append(buffer.name+".");
        if(op == NodeType.NOTIFY)sb.append("notify()\n");
        else sb.append("notifyAll()\n");

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
        sb.append("]\n"+st.nestIndent+"Notify Succ edges = [");
        for(BB f : this.notifySucc){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");
        
        return sb.toString();
    }
}
