class input3 {
    public static void main(String[] args) {
        int x;
        int y;

        boolean flag;
        int[] arr;

        A3 a1;
        A3 a2;
        A3 r1;
        A3 r2;
        A3 r3;

        x = 10;
        y = 15;

        a1 = new A3();
        r1 = a1;
        a2 = new A3();
        r2 = a2;

        r3 = new A3();
        flag = a1.bar(x,y);

        if (flag) {
            a1 = r3;
        }
        else {
            a1 = new A3();
        }

        /* r1 alias? r3 */
        /* a1 alias? r1 */
        /* a2 alias? r2 */
        /* a1 alias? a2 */
        {}
    }
}

class A3{
    A3 a;
    boolean flag;

    public boolean bar(int x,int y)
    {
        flag = x<y;
        return flag;
    }
}
