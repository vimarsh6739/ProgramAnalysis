package tools;

public class MsgWaitNode extends BB {
    Field buffer;
    BB waitingPred;
    BB notifiedEntry;
    
    public MsgWaitNode(NodeType op, int bbid,int tid, String ann, Field buffer,BB waitingPred,BB notifiedEntry) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
        this.waitingPred=waitingPred;
        this.notifiedEntry=notifiedEntry;
    }

    @Override
    public void updateInEdge(BB parent) {
        // Perform DFS
        this.inEdges.addAll(parent.flowInfo);
        this.flowInfo.add(this);

        this.waitingPred.updateInEdge(this);
        this.waitingPred.updateSummary();

        this.notifiedEntry.updateInEdge(this.waitingPred);
        this.notifiedEntry.updateSummary();
    }

    @Override
    public void updateSummary() {
        // Remove this from wait blk
        this.flowInfo.remove(this);

        // Propogate flow info as that of notifiedEntry
        this.flowInfo.addAll(this.notifiedEntry.flowInfo);
    }

    @Override
    public void updateOutEdge() {
        for(BB f : this.inEdges){
            f.outEdges.add(this);
        }

        this.waitingPred.updateOutEdge();
        this.notifiedEntry.updateOutEdge();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t");
        
        sb.append(buffer.name+".wait()\n");
        
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
        sb.append(this.waitingPred.toString());
        sb.append(this.notifiedEntry.toString());
        return sb.toString();
    }
}
