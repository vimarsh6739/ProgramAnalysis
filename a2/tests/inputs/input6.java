class Main4 {
    public static void main(String[] args) {
        A a;
        B b1;
        B b2;
        C c1;
        C c2;
        int y;

        a = new A();
        b1 = new B();
        b2 = new B();
        c1 = new C();
        c2 = new C();

        y = b1.set(a);
        y = b2.set(a);

        /* b1 alias? b2 */

        y = c1.set(b1);
        y = c2.set(b2);

        b2 = c1.getB();

        /* c1 alias? c2 */
        y = 4;
        System.out.println(y);
    }
}

class A {

}

class B extends A {
    A x;

    public int set(A a) {
        int t;

        x = a;

        t = 0;
        return t;
    }
}

class C extends B {
    B f;

    public B getB() {
        return f;
    }

    public int set(B b) {
        int t;
        C thisC;

        f = b;
        thisC = this;
        t = b.set(thisC);

        t = 0;
        return t;
    }
}