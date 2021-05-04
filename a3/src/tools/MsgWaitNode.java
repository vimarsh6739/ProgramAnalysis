package tools;

public class MsgWaitNode extends BB {
    Field buffer;
    BB waiting_pred;
    BB notified_entry;

    public MsgWaitNode(NodeType op, int bbid,int tid, Field buffer) {
        super(op, bbid,tid);
        this.buffer=buffer;
        this.waiting_pred=null;
        this.notified_entry=null;
    }

    public MsgWaitNode(NodeType op, int bbid,int tid, String ann, Field buffer) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
        this.waiting_pred=null;
        this.notified_entry=null;
    }
}
