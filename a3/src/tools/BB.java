package tools;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * General Basic Block
 */
public class BB {
    static SymbolTable st;      // link to global symbol table
    
    // Edges
    Set<BB> inEdges;
    Set<BB> flowInfo;
    Set<BB> outEdges;
    Set<BB> crossEdges;
    NodeType op;
    int bbid;                   // global id of current basic block
    String ann;                 // user annotation 
    int tid;                    // ThreadId(in PEG)

    public BB() {
        op=NodeType.NOP; bbid=tid=-1;
        inEdges=outEdges=flowInfo=crossEdges=null;
    }
    
    public BB(NodeType op, int bbid, int tid, String ann){
        this.op=op;
        this.bbid=bbid;
        this.tid=tid;
        this.ann = ann;
        this.inEdges    = new LinkedHashSet<>();
        this.flowInfo   = new LinkedHashSet<>();
        this.outEdges   = new LinkedHashSet<>();
        this.crossEdges = new LinkedHashSet<>();
    }

    /** Overridden method for recursive basic blocks */
    public void addNode(BB blk) {}
    
    /**
     * Overridden method for computing in-edges
     * @param parent
     */
    public void updateInEdge(BB parent){}

    /** Overridden method for computing flowInfo */
    public void updateSummary(){}

    /** Overridden method for computing out-edges */
    public void updateOutEdge(){
        for(BB f : this.inEdges){
            f.outEdges.add(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        return sb.toString();
    }
}
