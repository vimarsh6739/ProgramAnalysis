class input1 {
    public static void main(String[] args) {
        A1 a1;
        A1 a2;

        a1 = new A1();
        a2 = new A1();

        /* a2 alias? a1 */
        {}
    }
}

class A1{
    boolean flag;
}
