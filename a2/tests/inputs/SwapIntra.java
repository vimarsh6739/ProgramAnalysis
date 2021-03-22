class SwapIntra{
    public static void main(String [] args){
        A x;
        A y;
        A z;
        A t;
        int pt;
        pt=0;
        x = new A();
        y = new A();
        z = new A();
        t = new A();
        t = x;
        x = y;
        y = t;

        /* x alias? y */
        /* t alias? z */
        /* x alias? z */
        System.out.println(pt);
    }
}

class A{}