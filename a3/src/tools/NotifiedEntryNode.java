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
    public void initializeGenKill() {
        // Add Monitor(buffer) to kill set
        this.KILL.addAll(st.monitor.get(buffer));
    }
    
    @Override
    public void updateMHP() {
        int sinit;
        // Re-Compute GEN_notifyAll using Vimarsh's update equation
        this.GEN.clear();
        BB my_p = this.waitingPred;
        for(BB other_p : st.waitingNodes.get(buffer)){
            // Check if other_p MHP my_p
            if(my_p != other_p && other_p.M.contains(my_p)){
                for(BB r : st.notifyNodes.get(buffer)){
                    if(r.op == NodeType.NOTIFYALL){
                        // Collect all notifyAll nodes with current buffer
                        if(my_p.M.contains(r) && other_p.M.contains(r)){
                            this.GEN.add(st.waitingSucc.get(other_p));
                        }
                    }
                }
            }
        }

        // Compute M(using GEN_notifyAll), notifyPred and waitingPred
        sinit = this.M.size();
        Set<BB> tmpM = new LinkedHashSet<>();
        for(BB p: this.notifyPred){
            tmpM.addAll(p.OUT);
        }
        tmpM.retainAll(this.waitingPred.OUT);
        tmpM.addAll(this.GEN);
        M.addAll(tmpM);
        
        if(sinit != this.M.size()){
            st.changeM = true;
        }

        // Update OUT using M, GEN and KILL
        sinit = this.OUT.size();
        this.OUT.clear();
        this.OUT.addAll(this.M);
        this.OUT.addAll(this.GEN);
        this.OUT.removeAll(this.KILL);

        if(sinit != this.OUT.size()){
            st.changeOUT = true;
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
