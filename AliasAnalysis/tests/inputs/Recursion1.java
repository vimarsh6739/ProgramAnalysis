class Recursion1 {
    public static void main(String[] args) {
        
        N node; N n; N o; N p; N q;
        int zero;
        zero=0;
        node = new N();
        zero = node.set_val(zero); 
         
        n = node.recursive(i, node);
        o = node.next;
        p = o.next;
        q = p.next;

        /* n alias? o */
        /* o alias? p */
        /* p alias? q */
        /* n alias? node */
        System.out.println(zero);
    }
}

class N {
    int value;
    N next;
  
    public int set_val(int v) {
        value=v;
        return value;
    }

    public N recursive(int i, N m) {
        boolean b;
        int j; int ten; int one;
        N v; N w;
        w = m.next;
        ten=10;one=1;
        b = i < ten;
        
        /* v alias? w */
        /* v alias? m */
        if (b) {
            j = i + one;
            v = this.recursive(j, w);
        }
        else{
            v = m;
        }

        return m;
    }
}