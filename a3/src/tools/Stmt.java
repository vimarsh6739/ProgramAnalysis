package tools;

import java.util.ArrayList;
import java.util.List;

public class Stmt {
    List<Stmt> in;
    List<Stmt> out;

    public Stmt(Stmt s) {
        this.in = new ArrayList<>(s.in);
        this.out = new ArrayList<>(s.out);
    }

    Stmt copy(){
        return new Stmt(this);
    }
}
