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
    public void initializeGenKill() {
        Set<BB> waitingNodes = st.waitingNodes.get(buffer);
        if(this.op == NodeType.NOTIFYALL){
            this.KILL.addAll(waitingNodes);
        }
        else if(this.op == NodeType.NOTIFY){
            if(waitingNodes.size() == 1){
                this.KILL.addAll(waitingNodes);
            }
        }
        else{
            System.out.println("[DEBUG] : Wrong op code for node in MsgNotifyNode");
            System.out.println("[DEBUG] : BB"+this.bbid+" Thread:"+this.tid);
        }
    }
    
    @Override
    public void updateMHP() {
        int sinit = this.notifySucc.size();
        // Compute NotifySucc and add to worklist if necessary
        for(BB m_wtp : st.waitingNodes.get(buffer)){
            // Check if waiting predecessor runs in ||el with this notifyAll node
            if(this.M.contains(m_wtp)){
                // Add it's waiting successor to notifySucc
                BB w_succ = st.waitingSucc.get(m_wtp);
                this.notifySucc.add(w_succ);

                // Add this node to notifyPred of w_succ
                if(st.notifyPred.containsKey(w_succ)){
                    st.notifyPred.get(w_succ).add(this);
                }
                else{
                    System.out.println("[DEBUG] You messed up in the waitingSucc map in BB"+this.bbid+":\nIt contains -> BB"+w_succ.bbid);
                }
            }
        }
        
        if(sinit != this.notifySucc.size()){
            // Change in notifySucc, add it to the worklist
            st.changeNotifySucc = true;
            st.worklist.addAll(this.notifySucc);
        }

        // Compute M
        sinit = this.M.size();
        Set<BB> tmpM = new LinkedHashSet<>();
        for(BB parent : this.localPred){
            tmpM.addAll(parent.OUT);
        }
        this.M.addAll(tmpM);

        if(sinit != this.M.size()){
            st.changeM = true; 
        }

        // Recompute GEN for the current node
        this.GEN.clear();
        this.GEN.addAll(this.notifySucc);
        
        // Compute OUT
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
