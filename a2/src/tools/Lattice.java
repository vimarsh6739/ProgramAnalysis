package tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lattice {
    Set<Reference> bottom;     // set of all references
    Set<Reference> prev;       // previous points to set
    Set<Reference> curr;       // current points to set
    
    Lattice(List<Reference> allRefs){
        prev = new HashSet<Reference>();
        curr = new HashSet<Reference>();
        bottom = new HashSet<Reference>(allRefs);
    }

    void updateBottom(Reference t){this.bottom.add(t);}

    void meet(Lattice s){this.curr.addAll(s.curr);}

    void join(Lattice s){this.curr.retainAll(s.curr);}
    
    boolean hasChanged(){return this.curr.equals(this.prev);}

    void updatePrev(){this.prev = new HashSet<Reference>(this.curr);}
}
