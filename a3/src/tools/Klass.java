package tools;

import java.util.ArrayList;
import java.util.List;

public class Klass {
    String cname;
    boolean isThread;
    List<Field> cFields;
    List<Field> lFields;
    List<Stmt> cfg;             // CFG corresponding to curr class

    public Klass(String cname, boolean isThread) {
        this.cname = cname;
        this.isThread = isThread; 
        this.cFields = new ArrayList<>();
        this.lFields = new ArrayList<>(); 
        this.cfg     = new ArrayList<>();
    }
    
}
