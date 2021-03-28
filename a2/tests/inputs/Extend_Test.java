class Extend_Test
{
	public static void main(String[] arg)
	{
		boolean z;
		A a;
		a = new A();
		z = a.foo();
	}
}
class A extends Extend_Test
{
	A f;
	public boolean foo()
	{
		boolean t;
		boolean t1;
		int cnt;
		int v;
		int u;
		cnt = 9;
		u = 0;
		v = 1;
		t = cnt < u;
		t = !t;
		while(t)
		{
			t1 = !t;
			if(t1)
				f = new B();
			else
				f = this;
			t1 = f.foo();	
			/*f alias? f*/
		 	cnt = cnt-v;
		 	t = cnt < u;
		 	t= !t;
		}
		return t;
	}
}
class B extends A
{
	public boolean foo()
	{
		boolean u;
		A o1;
		f = new C();
		o1 = this;
		/*f alias? o1*/
		u = false;
		return u;
	}
}
class C extends B
{
}


