package tools;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SymbolTable {
    
    List<Clazz> cList;
    Map<String,Clazz> cMap;
    
    List<Field> gfList;
    Map<String,Field> fMap;
    
    int N_THREADS;
    int N_BLKS;
    Map<Field,Integer> thFieldMap;          // maps field to it's tid
    Map<Integer,Clazz> thClassMap;          // maps threadId to it's class
    Map<Integer,Deque<BB>> thStackMap;      // maps threadId to current stack value
    Map<Integer, List<BB>> thPEGMap;        // PEG for respective thread
    Map<Integer, BB> thLastStmt;            // last added toplevel statement 
    
    // Maps for worklist algo
    Map<Integer, BeginNode> thBeginBlks;    // threadId -> begin of PEG
    
    Map<BB,BB>      waitingPred;            // waiting pred map
    Map<BB,BB>      waitingSucc;            // waiting succ map
    
    Map<BB,Set<BB>> startPred;              // begin -> start
    Map<BB,Set<BB>> startSucc;              // start -> begin
    Map<BB,Set<BB>> notifyPred;             // notified-entry -> notify(All)
    Map<BB,Set<BB>> notifySucc;             // notify(All) -> notified-entry
    
    Set<Field> sync_objs;                   // set of synchronization objs
    Map<Field, Set<BB>> monitor;            // monitor map for sync buffers   
    Map<Field, Set<BB>> notifyNodes;        // set of notify(All) nodes for obj
    Map<Field, Set<BB>> waitingNodes;       // set of waiting nodes for obj
    Map<Integer,Set<BB>> N;                 // flattened peg per thread
    
    // Queries
    List<String> q_lhs;                 
    List<String> q_rhs;
    
    Clazz curr_class;
    boolean inRun;
    String nestIndent;
    public SymbolTable() {
        this.cList      = new ArrayList<>();
        this.cMap       = new HashMap<>();
        
        this.gfList     = new ArrayList<>();
        this.fMap       = new HashMap<>();
        
        this.thFieldMap     = new HashMap<>();
        this.thClassMap     = new HashMap<>();
        this.thStackMap     = new HashMap<>();
        this.thPEGMap       = new HashMap<>();
        this.thLastStmt     = new HashMap<>();
        this.thBeginBlks  = new HashMap<>();

        this.waitingPred = new HashMap<>();
        this.waitingSucc = new HashMap<>();
        this.startPred   = new HashMap<>();
        this.startSucc   = new HashMap<>();
        this.notifyPred  = new HashMap<>();
        this.notifySucc  = new HashMap<>();
        
        this.sync_objs = new LinkedHashSet<>();
        this.monitor = new HashMap<>();
        this.notifyNodes = new HashMap<>();
        this.waitingNodes = new HashMap<>();
        this.N = new HashMap<>();
        this.q_lhs  = new ArrayList<>();
        this.q_rhs  = new ArrayList<>();

        this.curr_class = null;
        this.inRun      = false;
        nestIndent = "";
    }
    
    /** Construct a new unique index for thread */
    int getTid(){return N_THREADS++;}
    
    /** Construct unique index for basic blk */
    int getBlkId(){return ++N_BLKS;}

    public void addClass(String cname, boolean isThread) {
        Clazz k = new Clazz(cname, isThread);
        this.cList.add(k);
        this.cMap.put(cname, k);
        this.curr_class = k;
    }
    
    public void addLocalField(String type, String var) {
        Field f;
        if(this.fMap.containsKey(var)){
            f = this.fMap.get(var);
        }
        else{
            f = new Field(type,var);
            this.gfList.add(f);
            this.fMap.put(var, f);
        }
        
        this.curr_class.lFields.add(f);
        f.scope.add(this.curr_class);             
    }
    
    public void addMemberField(String type, String var) {
        Field f;
        if(this.fMap.containsKey(var)){
            f = this.fMap.get(var);
        }
        else{
            f = new Field(type,var);
            this.gfList.add(f);
            this.fMap.put(var, f);
        }
        this.curr_class.cFields.add(f);
        f.scope.add(this.curr_class);
    }
    
    public void addThread(String var) {
        int tid = this.getTid();
        Field f = this.curr_class.getField(var);
        this.thFieldMap.put(f, tid);
    }

    public void updateThreads(){
        // Add class entried for each tid
        for(Field f : this.thFieldMap.keySet()){
            String cname = f.type;
            Clazz k = this.cMap.get(cname);
            int tid = this.thFieldMap.get(f);
            this.thClassMap.put(tid, k);
 
            List<BB> curr_cfg = new ArrayList<>();
            this.thPEGMap.put(tid, curr_cfg);

            Deque<BB> curr_stack = new ArrayDeque<>();
            this.thStackMap.put(tid, curr_stack);

            this.N.putIfAbsent(tid, new LinkedHashSet<>());
        }
        
        /** Set symbol table for all basic blocks */
        BB.st = this;
    }

    public void setClass(String cname) {this.curr_class = this.cMap.get(cname);}
    
    public void resetClass() {this.curr_class=null;}
    
    public void pushStack(int tid, BB blk){this.thStackMap.get(tid).push(blk);}

    public void popStack(){
        Deque<BB> t_stack;
        for(int tid : this.thStackMap.keySet()){
            if(this.thClassMap.get(tid) == this.curr_class){
                t_stack = this.thStackMap.get(tid);
                if(!t_stack.isEmpty()){t_stack.pop();}
            }
        }
    }

    void updateEntry(int tid, BB blk){
        Deque<BB> t_stack = this.thStackMap.get(tid);
        BB top;
        if(t_stack.isEmpty()){
            this.thPEGMap.get(tid).add(blk);
        }
        else{
            top = t_stack.peek();
            top.addNode(blk);
        }
    }

    /**
     * Add basic blocks to PEG
     * @param op        Basic block type
     * @param ann       Label
     * @param arg1      Arg1
     * @param arg2      Arg2
     * @param arg3      Arg3
     */
    public void addStatement(NodeType op, String ann, String arg1, String arg2, String arg3){
        // basic block to be added
        BB blk; 

        // Field value = null for constants or booleans
        Field f1,f2,f3;
        f1 = this.curr_class.getField(arg1);
        f2 = this.curr_class.getField(arg2);
        f3 = this.curr_class.getField(arg3);
        
        for(int tid : this.thClassMap.keySet()){
            if(this.thClassMap.get(tid) == this.curr_class){
                int bbid = this.getBlkId();

                switch(op){
                    case BEGIN:
                        blk = new BeginNode(op,bbid,tid);
                        this.updateEntry(tid, blk);
                        this.thBeginBlks.put(tid, (BeginNode)blk);
                        this.N.get(tid).add(blk);
                        break;
        
                    case END:
                        blk = new EndNode(op, bbid, tid);
                        this.updateEntry(tid, blk);
                        this.N.get(tid).add(blk);
                        break;
                    
                    case ADD:
                    case AND:
                    case FIELDASSIGN:
                    case FIELDREAD:
                    case LT:
                    case MULT:
                    case SUB:
                    case PRINT:
                    case ALLOCATE:
                    case ASSIGN:
                    case NOT:
                        blk = new StmtNode(op, bbid, tid, ann, f1, f2, f3,arg1,arg2,arg3);
                        this.updateEntry(tid, blk);
                        this.N.get(tid).add(blk);
                        break;

                    case BLOCK:
                        blk = new BlockNode(op, bbid, tid, ann);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid, blk);
                        this.N.get(tid).add(blk);
                        break;
        
                    case IF_ELSE:
                        blk = new IfElseNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid,blk);
                        this.N.get(tid).add(blk);
                        break;
                    
                    case WHILE:
                        blk = new WhileNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid, blk);
                        this.N.get(tid).add(blk);
                        break;
        
                    case SYNC:
                        this.sync_objs.add(f1);
                        this.monitor.putIfAbsent(f1, new LinkedHashSet<>());
                        this.notifyNodes.putIfAbsent(f1, new LinkedHashSet<>());
                        this.waitingNodes.putIfAbsent(f1, new LinkedHashSet<>());

                        BB entry_blk = new EntryNode(bbid, tid, ann, f1);
                        int body_bbid = this.getBlkId();
                        int exit_bbid = this.getBlkId();
                        BB exit_blk = new ExitNode(exit_bbid, tid, null, f1);
                        blk = new SynchronizeNode(op, body_bbid, tid, null, f1,entry_blk,exit_blk);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid, blk);
                        this.N.get(tid).add(entry_blk);
                        this.N.get(tid).add(blk);
                        this.N.get(tid).add(exit_blk);
                        break;
        
                    case START:
                        blk = new MsgStartNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        this.N.get(tid).add(blk);
                        break;
        
                    case JOIN:
                        blk = new MsgJoinNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        this.N.get(tid).add(blk);
                        break;
                        
                    case WAIT:
                        int waitPred_bbid = this.getBlkId();
                        int notEntry_bbid = this.getBlkId();
                        BB wait_pred_blk = new WaitingPredNode(waitPred_bbid, tid,f1);
                        BB not_entry_blk = new NotifiedEntryNode(notEntry_bbid, tid,f1);
                        blk = new MsgWaitNode(op, bbid, tid, ann, f1,wait_pred_blk,not_entry_blk);
                        this.updateEntry(tid, blk);

                        this.waitingNodes.get(f1).add(wait_pred_blk);
                        this.N.get(tid).add(blk);
                        this.N.get(tid).add(wait_pred_blk);
                        this.N.get(tid).add(not_entry_blk);
                        break;
        
                    case NOTIFY:
                    case NOTIFYALL:
                        blk = new MsgNotifyNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);

                        this.notifyNodes.get(f1).add(blk);
                        this.N.get(tid).add(blk);
                        break;
                        
                    default:
                        break;
                }
            }
        }
    }
    
    /**
     * MHP query
     * @param q1
     * @param q2
     */
    public void addQuery(String q1, String q2) {
        this.q_lhs.add(q1);
        this.q_rhs.add(q2);
    }

    /**
     * Builds the PEG and initializes the values of all constant sets
     */
    void buildPEG(){
        for(int tid : this.thClassMap.keySet()){
            List<BB> peg = this.thPEGMap.get(tid);

            // add localPred
            peg.get(0).updateSummary();
            for(int i = 1; i<peg.size(); ++i){
                peg.get(i).updateInEdge(peg.get(i-1));
                peg.get(i).updateSummary();
            }

            // add localSucc
            for(BB f : peg){
                f.updateOutEdge();
            }

            // add startPred and startSucc
            for(BB f : peg){
                f.updateStartEdge();
            }

            // update Monitor maps
            for(Field obj : this.sync_objs){
                for(BB blk : peg){
                    blk.updateMonitor(obj,false);
                }
            }
        }
    }

    void runWorklistAlgo(){

    }

    void outputQueries(){

    }

    public void analyze() {
        this.buildPEG();
        this.runWorklistAlgo();
        this.outputQueries();
    }

    public void printVariables() {
        String ts = "\t";
        System.out.println("Number of threads:"+N_THREADS);
        
        System.out.println("THREAD MAPPING");
        for(Field f : this.thFieldMap.keySet()){
            Integer tid = this.thFieldMap.get(f);
            Clazz k = this.thClassMap.get(tid);
            System.out.println(ts+"("+f.name+" : "+f.type+") -> " + tid.intValue()+" -> "+k.cname);
        }
        
        System.out.println("VARIABLE MAPPING");
        for(Clazz k: this.cList){
            System.out.println("Class " + k.cname);
            System.out.println("Members:");
            for(Field f : k.cFields){
                System.out.println(ts + f.name + " : "  + f.type);
                System.out.print(ts+"Present in -> {");
                for(Clazz k1 : f.scope){
                    System.out.print(k1.cname+",");
                }
                System.out.println("}");
            }
            System.out.println("Locals:");
            for(Field f : k.lFields){
                System.out.println(ts + f.name + " : "  + f.type);
                System.out.print(ts+"Present in -> {");
                for(Clazz k1 : f.scope){
                    System.out.print(k1.cname+",");
                }
                System.out.println("}");
            }
        }
    }

    public void printProgram() {
        for(int tid : this.thPEGMap.keySet()){
            System.out.println("THREAD "+tid+" : Class "+this.thClassMap.get(tid).cname);
            for(BB f : this.thPEGMap.get(tid)){
                System.out.print(f);
            }
            System.out.println("====================================================");
        }
        System.out.println("QUERIES:");
        for(int i=0;i<q_lhs.size();++i){
            System.out.println("MHP("+q_lhs.get(i)+","+q_rhs.get(i)+")");
        }
        System.out.println("====================================================");

    }

    public void printSyncVariables(){
        String ts = "\t";
        System.out.println("SYNCHRONIZATION MAPS:");
        int i=1;
        for(Field obj : this.sync_objs){
            System.out.println(i+". Sync buffer < "+obj.name+" : "+obj.type+" >");
            System.out.print(ts+"Monitor nodes = [");
            String delim="";
            for(BB f : monitor.get(obj)){
                System.out.print(delim+f.bbid);
                delim=",";
            }
            delim="";
            System.out.print("]\n"+ts+"Notify nodes = [");
            for(BB f : notifyNodes.get(obj)){
                System.out.print(delim+f.bbid);
                delim=",";
            }
            delim="";
            System.out.print("]\n"+ts+"Waiting nodes = [");
            for(BB f : notifyNodes.get(obj)){
                System.out.print(delim+f.bbid);
                delim=",";
            }
            delim="";
            System.out.print("]\n\n");
            ++i;
        }
        System.out.println("====================================================");
    }
}
