class input3 {
    public static void main(String[] args){
        A a;
        A b;
        A ret1;
        A ret2;
        A r1;
        A r2;
		A fo1;
		A fo2;
        a = new A();
        b = new A();
        ret1 = a.foo();
        ret2 = b.foo();
        a.f = ret1;
        b.f = ret2;
		r1 = a;
        r2 = b;
		fo1 = a.f;
		fo2 = b.f;
        /*a alias? b*/
        /*r1 alias? r2*/
        /*a alias? r1*/
        /*a alias? r2*/
        /*b alias? r1*/
        /*b alias? r2*/
		/*fo1 alias? fo2*/
        {}
    }    
}
class A{
    A f;
    public A foo(){
        A n;
        n = new A();
        return n;
    }
}