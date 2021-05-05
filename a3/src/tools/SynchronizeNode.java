package tools;

public class SynchronizeNode extends BB{
    Field buffer;
    BB entry;
    BB body;
    BB exit;
                                                                                
    public SynchronizeNode(NodeType op, int bbid,int tid, String ann, Field buffer, BB entry, BB exit) {
        super(op,bbid,tid,ann);
        this.buffer=buffer;
        this.entry=entry;
        this.body=null;
        this.exit=exit;
    }

    @Override
    public void addNode(BB blk) {
        this.body=blk;
    }

    @Override
    public void updateInEdge(BB parent) {
        // Short circuit connection to entry blk

        this.entry.updateInEdge(parent);
        this.entry.updateSummary();
        
        this.body.updateInEdge(this.entry);
        this.body.updateSummary();
        
        this.exit.updateInEdge(this.body);
        this.exit.updateSummary();
    }

    @Override
    public void updateSummary() {
        // outset of exit is the out of the current sync blk
        this.flowInfo.addAll(this.exit.flowInfo);
    }

    @Override
    public void updateOutEdge() {
        this.entry.updateOutEdge();
        this.body.updateOutEdge();
        this.exit.updateOutEdge();
    }

    @Override
    public void updateStartEdge() {
        this.body.updateStartEdge();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(entry.toString());
        sb.append(st.nestIndent+"BB"+bbid+":\t");
        sb.append("SYNCHRONIZED("+buffer.name+"){\n");
        st.nestIndent += "\t";
        sb.append(body.toString());
        sb.append(exit.toString());
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        sb.append(st.nestIndent+"END-SYNCHRONIZED}\tBB"+bbid+": \n\n");
        return sb.toString();
    }
}
