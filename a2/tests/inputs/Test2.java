
class Test2 {
	public static void main(String[] args) {
		A a1;
		A a2;
		A a3;
		A a4;
		A a5;
		A a6;
		C1 o1;
		C2 o2;
		C3 o3;
		C4 o4;
		C5 o5;
		a1 = new A();
		a3 = new A();
		a5 = new A();
		o1 = new C1();
		o3 = new C3();
		a2 = o3.m1(a1);
		/*a1 alias? a2*/
		a4 = o3.m2(a3);
		/*a4 alias? a3*/
		/*a6 alias? a5*/
		a6 = o1.m3(a5);
	}
}
class C1{
	A f0;
	public A m1(A x1) {
		f0 = new A();
		return f0;
		
	}
	public A m2(A x2) {
		return x2;
	}
	public A m3(A x3) {
		f0 = new A();
		return f0; 
		
	}
}
class C2 extends C1{
	public A m1(A x1){
		return x1;
		
	}
	
}
class C3 extends C1{
	public A m1(A x1) {
		A tmp;
		tmp = new A();
		return tmp;
		
	}
	
}
class C4 extends C3{
	public A m2(A x2) {
		A tmp;
		tmp = new A();
		return tmp;	
	}
	
}
class C5 extends C3{
	public A m3(A x3) {
		return x3;
	}
}
class A{
	int a;
}
