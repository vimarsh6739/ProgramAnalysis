package tools;

public class WaitingPredNode extends BB {
    Field buffer;
    public WaitingPredNode(int bbid,int tid,Field buffer) {
        super(NodeType.WAITING_PRED, bbid,tid,null);
        this.buffer = buffer;
    }
}
