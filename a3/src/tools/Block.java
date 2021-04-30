package tools;

import java.util.ArrayList;
import java.util.List;

public class Block extends BB{

    List<BB> sub_blocks;
    
    public Block(Operations op, int bbid) {
        super(op,bbid);
        this.sub_blocks =   new ArrayList<>();
    }

    public Block(Operations op, int bbid, String ann){
        super(op, bbid, ann);
        this.sub_blocks=new ArrayList<>();
    }

    public Block(Block b, int tid){
        this.op     =   b.op;
        this.bbid   =   b.bbid;
        this.ann    =   b.ann;
        this.tid    =   tid;

        this.inEdges    =   new ArrayList<>();
        this.outEdges   =   new ArrayList<>();
        this.sub_blocks =   new ArrayList<>();

        // Add a deep copy of all subblocks within
        for(int i=0;i<b.sub_blocks.size();++i) {
            this.sub_blocks.add(b.sub_blocks.get(i).copy(tid));
        }
    }

    @Override
    public BB copy(int tid){
        return new Block(this,tid);
    }
}
