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
}
