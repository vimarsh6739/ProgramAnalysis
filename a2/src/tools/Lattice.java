package tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lattice {
    Set<Reference> bottom;     // set of all references
    Set<Reference> prev;       // previous points to set
    Set<Reference> curr;       // current points to set
    
    /**
     * Default constructor
     * @param allRefs
     */
    public Lattice(List<Reference> allRefs) {
        this.prev = new HashSet<>();
        this.curr = new HashSet<>();
        this.bottom = new HashSet<>(allRefs);
    }

    /**
     * Default constructor
     * @param prev
     * @param curr
     * @param allRefs
     */
    public Lattice( Set<Reference> prev, 
                    Set<Reference> curr, 
                    List<Reference> allRefs) {
        this.prev = new HashSet<Reference>(prev);
        this.curr = new HashSet<Reference>(curr);
        this.bottom = new HashSet<Reference>(allRefs);
    }

    /**
     * Default constructor
     * @param prev
     * @param curr
     * @param allRefs
     */
    public Lattice( Set<Reference> prev, 
                    Set<Reference> curr, 
                    Set<Reference> allRefs) {
        this.prev = new HashSet<>(prev);
        this.curr = new HashSet<>(curr);
        this.bottom = new HashSet<>(allRefs);
    }

    /**
     * Copy constructor
     * @param other
     */
    public Lattice(Lattice other) {
        this(other.prev, other.curr, other.bottom);
    }

    /**
     * Non-typecasted deep copy
     */
    public Lattice copy(){
        return new Lattice(this);
    }

    void updateBottom(Reference t){this.bottom.add(t);}

    void meet(Lattice s){this.curr.addAll(s.curr);}

    void join(Lattice s){this.curr.retainAll(s.curr);}
    
    boolean hasChanged(){return this.curr.equals(this.prev);}

    void updatePrev(){this.prev = new HashSet<Reference>(this.curr);}

    String printValue() {
        StringBuilder sb = new StringBuilder("");
        String prefix="";
        for(Reference r : this.curr){
            sb.append(prefix);
            prefix =",";
            sb.append(" "+r.cname+"::"+r.ref_id);
        }
        return sb.toString();
    }
}
