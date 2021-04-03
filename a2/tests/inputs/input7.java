class input3 {
    public static void main(String[] args) {
        int temp;
        Temp a1;
        Temp a2;
        Temp a3;

        a1 = new Temp();
        temp = 3;
        temp = a1.setX(temp);
        a2 = a1;

        temp = a1.getX();
        System.out.println(temp);
        temp = a2.getX();
        System.out.println(temp);

        a2 = new Temp();
        temp = 5;
        temp = a2.setX(temp);

        temp = a1.getX();
        System.out.println(temp);
        temp = a2.getX();
        System.out.println(temp);

        a3 = new Temp();

        /* a1 alias? a2 */
        /* a2 alias? a3 */
        /* a1 alias? a3 */

        temp = 0;
    }
}

class Temp {
    int x;

    public int setX(int val) {
        x = val;
        return x;
    }

    public int getX() {
        return x;
    }
}