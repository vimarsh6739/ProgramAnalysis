package tools;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * General Basic Block
 */
public class BB {
    static SymbolTable st;      // link to global symbol table
    
    // Edges
    Set<BB> localPred;
    Set<BB> flowInfo;
    Set<BB> localSucc;

    // MHP sets
    Set<BB> M;
    Set<BB> OUT;
    Set<BB> GEN;
    Set<BB> KILL;

    NodeType op;
    int bbid;                   // global id of current basic block
    String ann;                 // user annotation 
    int tid;                    // ThreadId(in PEG)

    public BB() {
        op=NodeType.NOP; bbid=tid=-1;
        localPred=localSucc=flowInfo=null;
    }
    
    public BB(NodeType op, int bbid, int tid, String ann){
        this.op     =   op;
        this.bbid   =   bbid;
        this.tid    =   tid;
        this.ann    =   ann;
        this.localPred  = new LinkedHashSet<>();
        this.flowInfo   = new LinkedHashSet<>();
        this.localSucc  = new LinkedHashSet<>();
        
        // Allocate MHP sets
        this.M = new LinkedHashSet<>();
        this.OUT = new LinkedHashSet<>();
        this.GEN = new LinkedHashSet<>();
        this.KILL = new LinkedHashSet<>();

        st.M.put(this, this.M);
        st.OUT.put(this, this.OUT);
        st.GEN.put(this, this.GEN);
        st.KILL.put(this, this.KILL);
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
        for(BB f : this.localPred){
            f.localSucc.add(this);
        }
    }

    /** Overridden method for start edges between PEGs start */
    public void updateStartEdge(){}
    
    /** Initialize the GEN and KILL sets for various nodes */
    public void initializeGenKill(){}
    
    /**
     * Overridden method for computing monitor sets
     * @param obj Synchronization buffer
     * @param b   Membership flag
     */
    public void updateMonitor(Field obj, boolean b) {
        if(b) {st.monitor.get(obj).add(this);}
    }
    
    /** Overridden method to add initial entries in worklist */
    public void initializeWorklist() {}

    /** Overridden method for MHP Transfer functions */
    public void updateMHP() {
        // Updates MHP information for local predecessors

        // Update M using OUT of localPred
        int sinit = this.M.size();
        Set<BB> tmpM = new LinkedHashSet<>();
        for(BB parent : this.localPred){
            tmpM.addAll(parent.OUT);
        }
        this.M.addAll(tmpM);

        if(sinit != this.M.size()){
            st.changeM = true; 
        }
        
        // Update OUT using M, GEN and KILL
        sinit = this.OUT.size();
        this.OUT.clear();
        this.OUT.addAll(this.M);
        this.OUT.addAll(this.GEN);
        this.OUT.removeAll(this.KILL);

        if(sinit != this.OUT.size()){
            st.changeOUT = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BB"+this.bbid+" <NOP>");
        return sb.toString();
    }

}
