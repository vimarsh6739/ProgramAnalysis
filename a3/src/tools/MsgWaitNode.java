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
}
