class input1 {
    public static void main(String[] args){
        X x;
        X q;
        Y y;
        X r1;
        X r2;
        boolean checker;
        x = new X();
        y = new Y();
        checker = true;
        /*x alias? y*/
        if(checker){
            q = new X();
            r1 = q;
        }else{
            q = new X();
            r2 = q;
        }
        /*r1 alias? r2*/
        /*q alias? r1*/
        /*q alias? r2*/
        System.out.println(checker);
    }    
}
class X{
    int f;
    public int f0(){
        f = 0;
        return f;
    }
}
class Y{
    int g;
    public int g0(){
        g = 1;
        return g;
    }
}