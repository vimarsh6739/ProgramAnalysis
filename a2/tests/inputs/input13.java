class input3
{
	public static void main(String[] arg)
	{
		A a;
		A b;
		int x;
		a = new A();
		b = new B();
		x = a.foo(b);
	}
}
class A
{
	boolean p;
	B f;
	public int foo(A obj)
	{
		int v;
		p = true;
		/*p alias? v*/
		f = new B();
		v = obj.foo(f);
		return v;
		
	}
}
class B extends A
{
	A f1;
	public int foo(A obj)
	{
		int v;
		f1 = new C();
		/*obj alias? f*/
		v = obj.foo(f1);
		return v;
	}
}
class C extends B
{
	int t;
	public int foo(A obj)
	{
		int u;
		t = 10;
		/*f alias? f1*/
		/*obj alias? f*/
		obj = this;
		return t;
	}
}
