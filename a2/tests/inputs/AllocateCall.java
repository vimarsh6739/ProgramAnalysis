class AllocateCall{
    public static void main(String [] args){
        A x;
        A y;
        A t;
        boolean ret;
        int pt;
        pt=0;
        x = new A();
        y = new A();
        t = new A();
        ret = new A().swap(x,y,t);   
        t = x;

        /* x alias? y */
        /* t alias? y */
        /* x alias? t */
        System.out.println(pt);
    }
}

class A{
    public boolean swap(A arg1, A arg2, A temp){
        boolean _ret;
        temp = arg1;
        arg1=arg2;
        arg2=temp;
        /* arg2 alias? temp */
        _ret=true;
        return _ret;
    }
}