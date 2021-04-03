class a1 {
	public static void main(String[] args) {
		A a1;
		A a2;
		A a3;
		int x;
		int y;
		a1 = new A();
		x = a1.foo(a1);
		a2 = a1.f0;
		y = a2.bar(a1);
		a3 = a2.f1;
		/*a1 alias? a2*/
		/*a3 alias? a2*/
		/*a1 alias? a3*/
		System.out.println(x);
	}
}
class A{
	A f0;
	A f1;
	public int foo(A x1) {
		int x;
		x = 1;
		f0 = new A();
		x1.f0 = f0;
		return x;
	}
	public int bar(A x2){
		int y;
		y=0;
		f1 =new A();
		x2.f1 = f1;
		return y;
	}
}