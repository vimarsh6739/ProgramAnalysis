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
    Map<Integer,Deque<BlockNode>> thStackMap;  // maps threadId to current stack value
    Map<Integer, List<BB>> thPEGMap;    // PEG for respective thread
    Map<Integer, BB> thLastStmt;        // last added toplevel statement

    Klass curr_class;
    boolean inRun;
    
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
        System.out.println(type+ " " + var);
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

            Deque<BlockNode> curr_stack = new ArrayDeque<>();
            this.thStackMap.put(tid, curr_stack);
        }

        // Set st in BB
        BB.st = this;
    }

    public void setClass(String cname) {this.curr_class = this.cMap.get(cname);}
    
    public void resetClass() {this.curr_class=null;}
    
    public void addBlock(){

    }

    public void exitBlock(){
        Deque<BlockNode> t_stack;
        for(int tid : this.thStackMap.keySet()){
            if(this.thClassMap.get(tid) == this.curr_class){
                t_stack = this.thStackMap.get(tid);
                if(!t_stack.isEmpty()){t_stack.pop();}
            }
        }
    }

    List<BB> getLayerList(int tid){
        Deque<BlockNode> t_stack = this.thStackMap.get(tid);
        if(t_stack.isEmpty()){
            return this.thPEGMap.get(tid);
        }
        else{
            return t_stack.peek().subBlocks;
        }
    }

    /**
     * Add basic blocks to PEG
     * @param op    Basic block type
     * @param lbl   Label
     * @param f1    Arg1
     * @param f2    Arg2
     * @param f3    Arg3
     */
    public void addStatement(NodeType op, String ann, String f1, String f2, String f3){
        BB blk; 
        for(int tid : this.thClassMap.keySet()){
            if(this.thClassMap.get(tid) == this.curr_class){
                int bbid = this.getBlkId();
                List<BB> curr_layer = this.getLayerList(tid);

                switch(op){
                    case BEGIN:
                        break;
        
                    case END:
                        blk = new ThEndNode(op, bbid, tid);
                        curr_layer.add(blk);
                        break;
                    
                    case ADD:
                    case AND:
                    case FIELDASSIGN:
                    case FIELDREAD:
                    case LT:
                    case MULT:
                    case SUB:
                    case PRINT:
                        break;
        
                    case IF_ELSE:
                        break;
                    
                    case WHILE:
                        break;
        
                    case SYNC:
                        break;
        
                    case START:
                        break;
        
                    case JOIN:
                        break;
                        
                    case WAIT:
                        break;
        
                    case NOTIFY:
                        break;
                        
                    case NOTIFYALL:
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
}
