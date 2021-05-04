package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * General Basic Block
 */
public class BB {
    static SymbolTable st;      // link to global symbol table
    
    // Edges
    List<BB> inEdges;
    List<BB> flowInfo;
    List<BB> outEdges;

    NodeType op;
    int bbid;                   // global id of current basic block
    String ann;                 // user annotation 
    int tid;                    // ThreadId(in PEG)

    public BB() {
        op=NodeType.NOP; bbid=tid=-1;
        inEdges=outEdges=flowInfo=null;
    }
    
    public BB(NodeType op, int bbid, int tid, String ann){
        this.op=op;
        this.bbid=bbid;
        this.tid=tid;
        this.ann = ann;
        this.inEdges    = new ArrayList<>();
        this.flowInfo   = new ArrayList<>();
        this.outEdges   = new ArrayList<>();
        
    }

    /**
     * Overridden method for recursive basic blocks
     * @param blk
     */
	public void addNode(BB blk) {}

}
