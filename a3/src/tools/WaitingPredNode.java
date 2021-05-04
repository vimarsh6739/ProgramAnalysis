package tools;

public class WaitingPredNode extends BB {
    public WaitingPredNode(int bbid,int tid) {
        super(NodeType.WAIT_PRED, bbid,tid);
    }
}
