class a1 {
	public static void main(String[] args) {
		A a1;
		A a2;
		int x;
		a1 = new A();
		a2 = new A();
		/*a1 alias?a2*/
		x = a1.foo(a1);
		a1 = a2;
		a2 = a1.f0;
		System.out.println(x);
	}
}
class A{
	A f0;
	public int foo(A x1) {
		int x;
		x = 1;
		f0 = new A();
		x1.f0 = f0;
		return x;
	}
}