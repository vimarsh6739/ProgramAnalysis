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