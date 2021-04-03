class mainClass
{
	public static void main(String[] args)
	{
		A a1;
		B b1;
		C c1;
		B b4;
		
		boolean a;
		int b;

		a1 = new A();
		a = a1.getX();
		if (a)
		{
			b1 = new B();
			b4 = b1.foo();
			b = b1.getY();
		}

		else
		{
			c1 = new C();
			a1 = c1.bar(a1);
			b = c1.getY();
		}
		/* a1 alias? b1 */
		System.out.println(b);
		
	}
}

class A
{
	boolean x;
	A a5;
	A a6;
	public A foo()
	{
		a5 = new A();
		return a5;
	}
	public A bar(A a3)
	{
		a6 = new A();
		a3.a5 = a6;
		return a3;
	}
	public boolean getX()
	{
		x = true;
		return x;
	}
}

class B extends A
{	
	int y;
	B b3;
	public B foo()
	{
		b3 = new B();
		return b3;
	}
	public int getY()
	{
		y = 10;
		return y;
	}
}

class C extends B
{	
	C c2;
	int y;
	public A bar(A a4)
	{
		c2 = new C();
		a4.a5 = c2;
		return a4;
	}
	public int getY()
	{
		y = 20;
		return y;
	}
}
