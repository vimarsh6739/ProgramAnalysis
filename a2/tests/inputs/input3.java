class input4 {
    public static void main(String[] args) {
        A4 a1;
        A4 a2;
        B4 b1;

        boolean flag;

        a1 = new B4();
        b1 = new B4();

        flag = a1.foo(b1);
        a2 = b1.aClass;

        /* a2 alias? a1 */
        /* a2 alias? b1 */
        /* a1 alias? b1 */
        {}
    }
}

class A4 {
    boolean flag;

    public boolean foo(B4 b) {
        A4 a;
        a = this;
        
        b.aClass = a;
        return flag;
    }
}

class B4 extends A4 {
    A4 aClass;
    public boolean foo(B4 b) {
        b.aClass = b;
        return flag;
    }
}
