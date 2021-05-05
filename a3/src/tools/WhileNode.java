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
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": (while block)\t");
        
        sb.append("WHILE("+cond.name+"){\n");
        st.nestIndent += "\t";
        sb.append(body.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        sb.append(st.nestIndent+"}\tBB"+bbid+": (while block end)\n\n");
        
        return sb.toString();
    }
}
