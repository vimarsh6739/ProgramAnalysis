package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reference {
    String cname;   
    ClassInfo ci;               // Class definition of the current reference
    List<Field> mvars;          // Contains only RefField entries 
    Map<Field, Lattice> refMap; // Subset of sigma
    int ref_id;                  // id of reference in class
    
    /**
     * Default constructor
     * @param ci
     */
    public Reference(ClassInfo ci) {
        this.cname=ci.cname;
        this.ci=ci;
        this.ref_id = ci.getRefId();
        this.mvars = new ArrayList<>();
        this.refMap = new HashMap<>();
        
        // Create new Field object for every member variable
        if(!ci.cname.equals("int[]")) {
            for(Field f : ci.fields) {
                if(f instanceof MemberField) {
                    mvars.add(new RefField( f.type, f.name, ((MemberField)f).cname, this));
                } else if(f instanceof BasicMemberField){
                    mvars.add(f.copy());
                }
            }
        }
    }

    /**
     * Copy constructor for a reference
     * @param other
     */
    public Reference(Reference other) {
        this.cname = other.cname;
        this.ref_id = other.ref_id;
        this.ci = other.ci;
        this.mvars = new ArrayList<>();
        this.refMap = new HashMap<>();

        // Deep copy variables - but reference pointed to doesn't change
        for(Field of : other.mvars) {
            this.mvars.add(of.copy());
        }

        // Deep copy Lattice values - however, the reference pointed to is the old reference only
        for(Field of: other.refMap.keySet()){
            // Get corresponding key in this
            Field key = null;
            for(Field f : this.mvars){
                if(f instanceof RefField){
                    if( f.name.equals(of.name) && f.type.equals(of.type) && ((RefField)f).cname.equals(((RefField)of).cname)) {
                        key = f;
                        break;
                    }          
                }
            }
            this.refMap.put(key, other.refMap.get(of).copy());
        }
    
    }

    /**
     * Non-typecasted deep copy
     */
    public Reference copy(){
        return new Reference(this);
    }
    
    /**
     * Build lattice for current set
     * @param refList
     */
    void buildLattice(List<Reference> refList) {
        // Every Member variable of the class has a reference
        for(Field f : this.mvars) {
            if(f instanceof RefField){
                Lattice lv = new Lattice(refList);
                this.refMap.put(f, lv);
            }
        }
    }

    /**
     * Print current reference values
     * @return
     */
    String printValue() {
        StringBuilder sb = new StringBuilder("");
        String prefix="";
        for(Field f : this.mvars){
            sb.append(prefix);
            prefix =", ";
            sb.append(f.name);//+" : " + f.type + ")");
        }
        return sb.toString();
    }

    /**
     * Returns a field for corresponding MemberField in reference
     * @param keyF
     * @return Corresponding RefField
     */
    Field getField(Field keyF) {

        Field ans = null;
        
        if(keyF instanceof MemberField) {
            MemberField search = (MemberField)keyF;

            for(Field fr : this.mvars) {
                RefField t;
                // fr can be a BasicMemberField also
                if(fr instanceof RefField){
                    t = (RefField)fr;
                    if(t.owner.equals(this) && t.type.equals(search.type) && t.cname.equals(search.cname) && t.name.equals(search.name))
                    {
                        ans = t;
                        break;
                    }
                }
            }
        }

        return ans;
    }

    /**
     * Function for reference specific lattice
     * @param f of type {@code MemberField}
     * @return Lattice for field in current reference
     */
    Lattice getLattice(Field f){
        Field rf = this.getField(f);
        return this.refMap.get(rf);
    }
}
