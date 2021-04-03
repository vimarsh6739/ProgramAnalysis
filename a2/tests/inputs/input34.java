class Main2 {
    public static void main(String[] args) {
        A a;
        B b;
        int y;
        a = new A();
        b = new B();
        y = b.foo(a);
        /* a alias? b */
        y = 2;
        System.out.println(y);
    }
}

class A {
    A f1;
}

class B extends A {
    B f2;

    public int foo(A x) {
        int t;
        B temp;
        t = 0;
        temp = new B();
        x.f1 = temp;
        return t;
    }
}