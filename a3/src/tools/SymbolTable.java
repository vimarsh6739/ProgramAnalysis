package tools;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    
    List<Klass> cList;
    Map<String,Klass> cMap;
    
    List<Field> gfList;
    Map<String,Field> fMap;
    
    int N_THREADS;
    int N_BLKS;
    Map<Field,Integer> thFieldMap;      // maps field to it's tid
    Map<Integer,Klass> thClassMap;      // maps threadId to it's class
    Map<Integer,Deque<BB>> thStackMap;  // maps threadId to current stack value
    Map<Integer, List<BB>> thPEGMap;    // PEG for respective thread
    Map<Integer, BB> thLastStmt;        // last added toplevel statement
    
    Map<Field, List<BB>> monitor;       // monitor map for sync buffers   
    
    Klass curr_class;
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

        this.curr_class = null;
        this.inRun      = false;
        nestIndent = "";
    }
    
    /** Construct a new unique index for thread */
    int getTid(){return N_THREADS++;}
    
    /** Construct unique index for basic blk */
    int getBlkId(){return ++N_BLKS;}

    public void addClass(String cname, boolean isThread) {
        Klass k = new Klass(cname, isThread);
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
            Klass k = this.cMap.get(cname);
            int tid = this.thFieldMap.get(f);
            this.thClassMap.put(tid, k);
 
            List<BB> curr_cfg = new ArrayList<>();
            this.thPEGMap.put(tid, curr_cfg);

            Deque<BB> curr_stack = new ArrayDeque<>();
            this.thStackMap.put(tid, curr_stack);
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
                        break;
        
                    case END:
                        blk = new EndNode(op, bbid, tid);
                        this.updateEntry(tid, blk);
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
                        break;

                    case BLOCK:
                        blk = new BlockNode(op, bbid, tid, ann);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid, blk);
                        break;
        
                    case IF_ELSE:
                        blk = new IfElseNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid,blk);
                        break;
                    
                    case WHILE:
                        blk = new WhileNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid, blk);
                        break;
        
                    case SYNC:
                        BB entry_blk = new EntryNode(bbid, tid, ann, f1);
                        int body_bbid = this.getBlkId();
                        int exit_bbid = this.getBlkId();
                        BB exit_blk = new ExitNode(exit_bbid, tid, null, f1);
                        blk = new SynchronizeNode(op, body_bbid, tid, null, f1,entry_blk,exit_blk);
                        this.updateEntry(tid, blk);
                        this.pushStack(tid, blk);
                        break;
        
                    case START:
                        blk = new MsgStartNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        break;
        
                    case JOIN:
                        blk = new MsgJoinNode(op, bbid, tid, ann, f1);
                        // System.out.println(arg1);
                        // System.out.println(f1.name+" "+f1.type);
                        this.updateEntry(tid, blk);
                        break;
                        
                    case WAIT:
                        int waitPred_bbid = this.getBlkId();
                        int notEntry_bbid = this.getBlkId();
                        BB wait_pred_blk = new WaitingPredNode(waitPred_bbid, tid,f1);
                        BB not_entry_blk = new NotifiedEntryNode(notEntry_bbid, tid,f1);
                        blk = new MsgWaitNode(op, bbid, tid, ann, f1,wait_pred_blk,not_entry_blk);
                        this.updateEntry(tid, blk);
                        break;
        
                    case NOTIFY:
                    case NOTIFYALL:
                        blk = new MsgNotifyNode(op, bbid, tid, ann, f1);
                        this.updateEntry(tid, blk);
                        break;
                        
                    default:
                        break;
                }
            }
        }
    }

    public void printVariables() {
        String ts = "\t";
        System.out.println("Number of threads:"+N_THREADS);
        
        System.out.println("THREAD MAPPINGS");
        for(Field f : this.thFieldMap.keySet()){
            Integer tid = this.thFieldMap.get(f);
            Klass k = this.thClassMap.get(tid);
            System.out.println(ts+"("+f.name+" : "+f.type+") -> " + tid.intValue()+" -> "+k.cname);
        }
        
        System.out.println("VARIABLE MAPPINGS");
        for(Klass k: this.cList){
            System.out.println("Class " + k.cname);
            System.out.println("Members:");
            for(Field f : k.cFields){
                System.out.println(ts + f.name + " : "  + f.type);
                System.out.print(ts+"Present in -> {");
                for(Klass k1 : f.scope){
                    System.out.print(k1.cname+",");
                }
                System.out.println("}");
            }
            System.out.println("Locals:");
            for(Field f : k.lFields){
                System.out.println(ts + f.name + " : "  + f.type);
                System.out.print(ts+"Present in -> {");
                for(Klass k1 : f.scope){
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
    }
}
