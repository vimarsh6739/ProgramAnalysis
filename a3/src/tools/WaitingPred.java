package tools;

public class WaitingPred extends BB {
    public WaitingPred(int tid) {
        super(Operations.WAIT_PRED, -1);
        this.tid=tid;
    }
}
