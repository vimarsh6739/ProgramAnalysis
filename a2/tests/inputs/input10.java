class input5 {
    public static void main(String[] args){
        B a1;
        B a2;
        B a3;
        B r1;
        B r2;
        B r3;
        A w;
        A x;
        B y;
        B z;
        a1 = new B();
        a2 = new B();
        a3 = new B();
        r1 = a1.foo(a1);
        r2 = a2.foo(a2);
        r3 = a3.foo(a3);
        a2.f0 = r1;
        w = a1.f0;
        x = a2.f0;
        y = a3.f1;
        z = a2.f1;
        /*w alias? x*/
        /*y alias? z*/
        {}
    }
}
class A{
    A f0;
    public A foo(A x){
        f0 = new A();
        return f0;
    }
}
class B extends A{
    B f1;
    public B foo(A x){
        f1 = new B();
        x.f0 = f1;
        return f1;
    }
}