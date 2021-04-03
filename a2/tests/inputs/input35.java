class input4 {
    public static void main(String[] args){
        A o1;
        A o2;
        A r1;
        A r2;
        A y;
        A x;
        A z;
        boolean checker;
        o1 = new A();
        o2 = new A();
        y = new A();
        /*o1 alias? o2*/
        checker = true;
        if(checker){
            x = o1;
            r1 = x;
        }else{
            x = o2;
            r2 = x;
        }
        x.f = y;
        z = x.f;
        /*r1 alias? r2*/
        /*x alias? o1*/
        /*x alias? o2*/
        /*z alias? y*/
        System.out.println(checker);
    }    
}
class A{
    A f;
}
