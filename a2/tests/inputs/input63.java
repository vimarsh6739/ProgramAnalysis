class input2 {
    public static void main(String[] args){
        A a;
        A b;
        A r1;
        boolean checker;
        a = new A();
        b = new A();
        checker = false;
        /*a alias? b*/ 
        if(checker){
            r1 = a;
        }else{
            r1 = b;
        }
        /*r1 alias? a*/
        /*r1 alias? b*/
        System.out.println(checker);
    }
}
class A{
    int f0;
    public int f0(){
        f0 = 0;
        System.out.println(f0);
        return f0;
    }
}
