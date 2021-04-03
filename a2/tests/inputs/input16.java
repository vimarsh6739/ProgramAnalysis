class mainClass
{
	public static void main(String[] args)
	{
		A a1;
		A a2;
		B b1;
		B b2;
		A a5;
		int x;
		b2 = new B();
		a2 = new A();
		b1 = a2.getf1(b2);
		a1 = b2.getf3(a2);
		/* a1 alias? a2 */
		/* b1 alias? b2 */
		/* a2 alias? b2 */
		
		a5 = new A();
		b2 = a5.getf2(b2);
		/* a5 alias? a1 */
		x=5;
	   	System.out.println(x);
	}
}

class A
{	
	B f1;
	B f2;

	public B getf1(B b3)
	{
		f1 = new B();
		b3.f3 = f1;
		return b3;
	}
	public B getf2(B b4)
	{
		f2 = new B();
		b4.f4 = f2;
		return b4;
	}

	
}

class B extends A
{	
	B f3;
	B f4;
	public A getf3(A a3)
	{
	 	f3 = new B();
	 	a3.f1 = f3;
	 	return a3;
	}
	public A getf4(A a4)
	{
		f4 = new B();
		a4.f2=f4;
		return a4;
	}
}
