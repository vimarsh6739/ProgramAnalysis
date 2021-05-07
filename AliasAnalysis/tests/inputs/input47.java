class mainClass
{
	public static void main(String[] args)
	{
	 	X x1;
	 	Y y1;
	 	X x2;
	 	Y y2;
	 	boolean val;
	 	int temp;
	 	int t;
	 	boolean c;
	 	t=1;
	 	c = true;
	 	x1 = new X();
	 	y1 = new Y();
	 	x2 = x1.foo (x1);
	 	x1 = y1.foo (x1);
	 	y2 = x1.bar (y1);
	 	/* x1 alias? x2 */
	 	/* y1 alias? x1 */
	 	/* y1 alias? y2 */
	 	val = y1.getVal();
	 	while (val)
	 	{
	 		System.out.println(val);
	 		temp = x1.setVal(x1,t);
	 		t = t + t;
	 		if (c)
	 		{
	 			val = y1.getVal();
	 			c=false;
	 			x1 = y1.foo (x2);
	 		}
	 		else
	 		{
	 			val = x1.getVal();
	 			y1 = x1.bar (y2);
	 		}
	 	}
	 		   	
	}
}

class X
{	
	X f0;
	X f2;
	Y f3;
	int v;
	int ret;
	boolean val;
	public X foo ( X x )
	{
		f0 = new X();
		x = f0;
		return f0;
	}
	public Y bar (Y y)
	{
		f3 = new Y();
		y.f3 = f3;
		return y;
	}
	public int setVal(X x,int v)
	{
		ret = 0;
		x.v = v;
		return ret;
	}
	public boolean getVal()
	{
		val = false;
		return val;
	}
}

class Y extends X
{
	Y f1;
	boolean v;
	public X foo ( X x )
	{
		f1 = new Y();
		x.f0 = f1;
		return x;
	}
	public boolean getVal()
	{
		v = true;
		return v;
	}


}
