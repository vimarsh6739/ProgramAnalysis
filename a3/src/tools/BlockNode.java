package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockNode extends BB{

    List<BB> subBlocks;

    public BlockNode(NodeType op, int bbid, int tid, String ann){
        super(op, bbid,tid, ann);
        this.subBlocks=new ArrayList<>();
    }

    @Override
    public void addNode(BB blk){
        this.subBlocks.add(blk);
    }

    @Override
    public void updateInEdge(BB parent) {
        this.localPred.addAll(parent.flowInfo);
        // Recurse on subBlocks
        if(!this.subBlocks.isEmpty()){
            // Short circuit connection from parent to first sub-block
            BB prev = parent;
            for(BB f : this.subBlocks){
                f.updateInEdge(prev);
                f.updateSummary();
                prev = f;
            }
        }
    }

    @Override
    public void updateSummary() {
        if(this.subBlocks.isEmpty()){
            // empty block <fout = in>
            this.flowInfo.addAll(this.localPred);
        }
        else{
            // non-empty block <fout = fout(subBlocks[-1])>
            int idx = this.subBlocks.size()-1;
            Set<BB> out_blks = this.subBlocks.get(idx).flowInfo;
            this.flowInfo.addAll(out_blks);
        }
    }

    @Override
    public void updateOutEdge() {
        for(BB f : this.localPred){
            f.localSucc.add(this);
        }

        // Recurse for subBlocks
        for(BB sblk : this.subBlocks){
            sblk.updateOutEdge();
        }
    }

    @Override
    public void updateStartEdge() {
        for(BB sblk : this.subBlocks){
            sblk.updateStartEdge();
        }
    }

    @Override
    public void updateMonitor(Field obj, boolean b) {
        if(b){
            st.monitor.get(obj).add(this);
        }

        for(BB sblk : this.subBlocks){
            sblk.updateMonitor(obj, b);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(st.nestIndent+"BB"+bbid+": BLOCK {\n");
        st.nestIndent += "\t";
        for(BB f : this.subBlocks){
            sb.append(f.toString()+"\n");
        }
        st.nestIndent = st.nestIndent.substring(0, st.nestIndent.length()-1);
        sb.append(st.nestIndent+"END-BLOCK }\tBB"+bbid+":\n\n");
        return sb.toString();
    }
}
