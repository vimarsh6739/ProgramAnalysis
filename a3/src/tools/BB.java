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
        op=NodeType.NOP;
        inEdges=null;
        outEdges=null;
        ann=null;
        bbid=-1;
        tid=-1;
    }

    public BB(NodeType op, int bbid, int tid) {
        this.op = op;
        this.bbid = bbid;
        this.tid = tid;
        this.inEdges = new ArrayList<>();
        this.flowInfo = new ArrayList<>();
        this.outEdges = new ArrayList<>();
    }

    public BB(NodeType op, int bbid, int tid, String ann){
        this(op, bbid,tid);
        this.ann = ann;
    }

}
