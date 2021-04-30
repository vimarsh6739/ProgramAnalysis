package tools;

public class NotifiedEntry extends BB {
    public NotifiedEntry(int tid) {
        super(Operations.NOTIFY_ENTRY,-1);
        this.tid=tid;
    }
}
