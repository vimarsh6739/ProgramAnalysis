class input5 {
    public static void main(String[] args) {
        A5 a1;
        A5 a2;
        A5 a3;

        B5 b1;
        B5 b2;
        B5 b3;

        B5 r1;
        B5 r2;

        boolean flag1;
        boolean flag2;

        flag1 = true;
        flag2 = false;

        a1 = new A5();
        a2 = new A5();

        b1 = new B5();
        b2 = new B5();
        b3 = new B5();

        while (flag1)
        {
            if(flag2) {
                a1.b = b1;
            }
            else {
                b1 = b3;
                b2 = b3;
            }
        }

        r1 = a1.b;
        r2 = a2.b;

        /* b3 alias? r1 */
        /* b3 alias? r2 */
        /* a1 alias? a2 */
        {}
    }
}

class A5 {
    B5 b;
}

class B5{
    B5 b;
}
