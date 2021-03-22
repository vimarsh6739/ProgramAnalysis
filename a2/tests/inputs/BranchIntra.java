class BranchIntra {
    public static void main(String[] args) {
        
        A x; A x1; A x2; A tx;
        B y; B y1; B ty;
        C z; 
        int p;
        boolean f;
        
        x = new A();
        x1 = new A();
        x2 = new A();
        y = new B();
        y1 = new B();
        z = new C();
        
        y.f0 = x1;  
        y1.f0 = x2;
        z.f0 = x2;

        x1.f1 = y1;
        x2.f1 = y;
        x.f1 = y;

        f = x.getf();

        if(f){
            y.f0 = x;
        } else {
            z.f0 = x1;
            x2 = x;
        }
        
        x1 = y.f0;
        x2 = y1.f0;
        x = z.f0;
        tx = z.f0;
        ty = x.f1;

        /* tx alias? x1 */
        /* tx alias? x */
        /* x alias? x2 */
        /* y alias? y1 */
        /* y1 alias? ty */
        p = 1;
        System.out.println(p);
    }    
}

class A {
    B f1;
    public boolean getf(){
        boolean b;
        b = true;
        return b;
    }
}

class B {
    A f0;
}

class C {
    A f0;
}