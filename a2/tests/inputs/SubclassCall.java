 class MultiAllocate {
    public static void main(String[] args) {
        t a;
        t b;
        int v;
        v=1;
        a = new t();
        b = a.n();
        a = b.n();
        a = a.n();
        /* a alias? b */
        System.out.println(v);
    }
}

class t {
    public t n() {
        t b1;
        t c1;
        int v;
        v=0;
        c1 = this;
        b1 = new s();
        /* b1 alias? c1 */
        System.out.println(v);
        return b1; 
    }
}

class s extends t {
    public t n() { 
        t b2;
        t c2;
        int v;
        v=0;
        c2 = this;
        b2 = new s();
        /* b2 alias? c2 */
        System.out.println(v);
        return b2; 
    }
}
