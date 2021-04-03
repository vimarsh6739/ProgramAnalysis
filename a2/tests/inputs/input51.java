class input2 {
    public static void main(String[] args) {
        A a1;
        A a2;
        B b1;
        B b2;

        a1 = new A();
        a2 = new A();

        b1 = new B();
        a1.val = b1;
        a2.val = b1;

        /* a1 alias? a2 */

        b1 = a1.val;
        b2 = a2.val;

        /* b1 alias? b2 */

        {}
    }
} 

class A {
    B val;
}

class B {
}