class Main1 {
    public static void main(String[] args) {
        AB a;
        AB b;
        int y;

        a = new AB();
        b = new AB();

        y = 0;
        y = a.setNum(y);

        y = 1;
        y = b.setNum(y);

        /* a alias? b */
        a = b;

        y = 1;
        System.out.println(y);
    }
}

class AB {
    int num;

    public int setNum(int n) {
        int x;
        x = 0;
        num = n;
        return x;
    }
}