package tools;

import java.util.LinkedHashSet;
import java.util.Set;

public class MsgStartNode extends BB {
    Field tField;
    Set<BB> startSucc;
    
    public MsgStartNode(NodeType op, int bbid,int tid, String ann, Field tField) {
        super(op,bbid,tid,ann);
        this.tField=tField;
        this.startSucc = new LinkedHashSet<>();
        st.startSucc.put(this, this.startSucc);
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
    public void updateStartEdge() {
        int tid = st.thFieldMap.get(tField);
        // Initial BB is always of type entry
        BeginNode th_begin_blk = st.thBeginBlks.get(tid);
        this.startSucc.add(th_begin_blk);
        th_begin_blk.startPred.add(this);
    }

    @Override
    public void initializeGenKill() {
        // Add all start successors to GEN
        this.GEN.addAll(this.startSucc);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t");
        
        sb.append(tField.name+".start()\n");

        sb.append(st.nestIndent+"Local In edges = [");
        String delim = "";
        for(BB f : this.localPred){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"Local Out edges = [");
        delim = "";
        for(BB f : this.localSucc){
            sb.append(delim + f.bbid);
            delim = ",";
        }
        sb.append("]\n"+st.nestIndent+"Start succ edges = [");
        delim="";
        for(BB f : this.startSucc){
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
