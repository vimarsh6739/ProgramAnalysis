class input2 {
    public static void main(String[] args) {
        boolean flag;

        A2 a1;
        A2 a2;
        A2 a3;

        a1 = new A2();
        a2 = new A2();
        a3 = a1;
        a3 = new A2();

        /* a3 alias? a1 */
        /* a2 alias? a1 */
        {}
    }
}

class A2{
    boolean flag;
}
