class Main3 {
    public static void main(String[] args) {
        A a;
        A b;
        int y;
        Boolean t;

        a = new A();
        b = new A();

        y = a.set(b);
        t = false;
        if (t) {
            y = a.set(b);
        } else {
            y = b.set(a);
        }

        y = a.setNext();
        y = b.setNext();

        /* a alias? b */

        y = 3;
        System.out.println(y);
    }
}

class A {
    A f;

    public int set(A x) {
        int t;
        t = 0;
        f = x;
        return t;
    }

    public int setNext() {
        int t;
        t = 0;
        f = this;
        return t;
    }
}