package tools;

import java.util.LinkedHashSet;
import java.util.Set;

public class BeginNode extends BB {

    Set<BB> startPred;
    
    public BeginNode(NodeType op,int bbid,int tid) {
        super(op,bbid,tid,null);
        this.startPred = new LinkedHashSet<>();
        st.startPred.put(this, this.startPred);
    }

    @Override
    public void updateSummary() {
        this.flowInfo.add(this);
    }

    @Override
    public void updateMHP() {
        int sinit = this.M.size();
        Set<BB> tmpM = new LinkedHashSet<>();
        for(BB parent : this.startPred){
            tmpM.addAll(parent.OUT);
        }

        // Remove current thread's nodes from M
        tmpM.removeAll(st.N.get(this.tid));
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
        sb.append("BB"+bbid+":\t");
        
        sb.append("BEGIN\n");

        sb.append("Local Pred edges = [");
        String delim = "";
        for(BB f : this.localPred){
            sb.append(delim+f.bbid);
            delim = ",";
        }
        sb.append("]\nLocal Succ edges = [");
        delim = "";
        for(BB f : this.localSucc){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\nStart Pred edges = [");
        delim="";
        for(BB f : st.startPred.get(this)){
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