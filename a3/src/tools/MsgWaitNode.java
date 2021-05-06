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
        
        if(this.waitingPred instanceof WaitingPredNode){
            WaitingPredNode tmp = (WaitingPredNode)this.waitingPred;
            tmp.waitingSucc = this.notifiedEntry;
            st.waitingSucc.put(this.waitingPred, this.notifiedEntry);
        }
        else{
            System.out.println("Wrong type for waitingPred");
        }
        
        if(this.notifiedEntry instanceof NotifiedEntryNode){
            NotifiedEntryNode tmp = (NotifiedEntryNode)this.notifiedEntry;
            tmp.waitingPred = this.waitingPred;
            st.waitingPred.put(this.notifiedEntry, this.waitingPred);
        }
        else{
            System.out.println("Wrong type for notifiedEntry");
        }
    }

    @Override
    public void updateInEdge(BB parent) {
        // Perform DFS
        this.localPred.addAll(parent.flowInfo);
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
        for(BB f : this.localPred){
            f.localSucc.add(this);
        }

        this.waitingPred.updateOutEdge();
        this.notifiedEntry.updateOutEdge();
    }

    @Override
    public void updateMonitor(Field obj, boolean b) {
        if(b){
            st.monitor.get(obj).add(this);
        }

        this.waitingPred.updateMonitor(obj, b);
        this.notifiedEntry.updateMonitor(obj, b);
    }

    @Override
    public void initializeGenKill() {
        this.waitingPred.initializeGenKill();
        this.notifiedEntry.initializeGenKill();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": Label:"+this.ann+"\t");
        
        sb.append(buffer.name+".wait()\n");
        
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
        sb.append(this.waitingPred.toString());
        sb.append(this.notifiedEntry.toString());
        return sb.toString();
    }
}
