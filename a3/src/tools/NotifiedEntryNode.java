package tools;

import java.util.LinkedHashSet;
import java.util.Set;

public class NotifiedEntryNode extends BB {
    Field buffer;
    Set<BB> notifyPred;
    BB waitingPred;
    
    public NotifiedEntryNode(int bbid,int tid,Field buffer) {
        super(NodeType.NOTIFIED_ENTRY,bbid,tid,null);
        this.buffer=buffer;
        this.notifyPred = new LinkedHashSet<>();
        st.notifyPred.put(this, this.notifyPred);
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
    public void updateMonitor(Field obj, boolean b) {
        if(b && obj != buffer){
            st.monitor.get(obj).add(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+":\t");
        
        sb.append("("+buffer.name+",notified-entry)\n");

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
        sb.append("]\n"+st.nestIndent+"Notify Pred edges = [");
        for(BB f : this.notifyPred){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n\n");
        
        return sb.toString();
    }
}
