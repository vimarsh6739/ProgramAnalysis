class mainClass
{
	public static void main(String[] args)
	{
		X x1;
		Y y1;
		Y y2;
		Z z1;
		X x2;
		int x;
		x1 = new X();
		y1 = new Y();
		y2 = new Z();
		z1 = new Z();  
		y1 = y2.foo(y1);
		/* x1 alias? y1 */
		x1 = z1.bar(x1);
		x2 = y1.bar(x1);
		/* x1 alias? z1 */
		/* y2 alias? x1 */
	   	x = x1.getA();
	   	System.out.println(x);
	}
}

class X
{	
	X f0;

	int a;
	public X bar(X x6)
	{
		f0 = new Y();
		x6.f0=f0;
		return x6;
	}
	public int getA()
	{	
		a = 5;
		return a;
	}	
}

class Y extends X
{
	Y f2;
	Y f3;
	public Y foo(Y y3)
	{
		f2 = new Y();
		y3.f2 = f3;
		return f2;
		
	}
	public X bar(X x5)
	{
		f3 = new Y();
		x5.f0 = f3;
		return x5;
	}	
}

class Z extends Y
{	
	Z f1;
	public X bar(X x6)
	{	
		f1 = new Z();
		x6.f0 = f1;
		return x6; 
		
	}
}
