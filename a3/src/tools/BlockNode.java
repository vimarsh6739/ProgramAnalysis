package tools;

import java.util.ArrayList;
import java.util.List;

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
