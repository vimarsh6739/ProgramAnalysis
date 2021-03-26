package tools;

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

    /**
     * Set the final value of Lattice
     * @param t
     */
    void updateBottom(Reference t){this.bottom.add(t);}

    /**
     * Meet operation
     * @param s
     */
    void meet(Lattice s){this.curr.addAll(s.curr);}

    /**
     * Join operation 
     * @param s
     */
    void join(Lattice s){this.curr.retainAll(s.curr);}
    
    /**
     * Check if lattice value has changed
     * @return boolean value
     */
    boolean hasChanged(){return !this.curr.equals(this.prev);}

    /**
     * Update previous value to current
     */
    void updatePrev(){this.prev = new HashSet<Reference>(this.curr);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        String prefix="";
        for(Reference r : this.curr){
            sb.append(prefix);
            prefix =",";
            sb.append(" "+r.cname+"@"+r.ref_id);
        }
        return sb.toString();
    }

    /**
     * Add the given reference to the current set
     * @param r
     */
    void addRef(Reference r) {this.curr.add(r);}
}
