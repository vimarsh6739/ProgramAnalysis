package tools;

public class WhileNode extends BB {
    Field cond;
    BB body;

    public WhileNode(NodeType op, int bbid,int tid, String ann, Field cond) {
        super(op,bbid,tid,ann);
        this.cond=cond;
        this.body=null;
    }

    @Override
    public void addNode(BB blk) {
        this.body = blk;
    }

    @Override
    public void updateInEdge(BB parent) {
        this.localPred.addAll(parent.flowInfo);
        this.flowInfo.add(this);

        this.body.updateInEdge(this);
        this.body.updateSummary();
    }

    @Override
    public void updateSummary() {
        // Add the natural loop!
        this.localPred.addAll(this.body.flowInfo);
    }

    @Override
    public void updateOutEdge() {
        // Takes care of self loops also
        for(BB f : this.localPred){
            f.localSucc.add(this);
        }

        // Recurse on body
        this.body.updateOutEdge();
    }

    @Override
    public void updateStartEdge() {
        this.body.updateStartEdge();
    }

    @Override
    public void updateMonitor(Field obj, boolean b) {
        if(b){
            st.monitor.get(obj).add(this);
        }

        this.body.updateMonitor(obj, b);
    }

    @Override
    public void initializeGenKill() {
        this.body.initializeGenKill();
    }

    @Override
    public void initializeWorklist() {
        this.body.initializeWorklist();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+":\t");
        
        sb.append("WHILE("+cond.name+"){\n");
        
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
        st.nestIndent += "\t";
        sb.append(body.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);

        sb.append(st.nestIndent+"}ENDWHILE -> BB"+bbid+"\n\n");
        
        return sb.toString();
    }
}
