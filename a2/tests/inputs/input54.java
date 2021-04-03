class mainClass
{
	public static void main(String[] args)
	{
		ABC a1;
		ABC a2;
		BCD b1;
		ABC a3;
		int a;
		int b;
		a1 = new ABC();
		a = a1.get1(); 
		b1 = new BCD();
	   	b = b1.get1();
	   	/* a1 alias? b1 */
	   	a2 = a1.get2(a1);
	   	a3 = new ABC();
	   	a3 = b1.get2(a1);
	   	/* a2 alias? a3 */
	   	System.out.println(a);
	   	System.out.println(b);
	}
}

class ABC
{
	ABC f0;
	public int get1()
	{
		int ret;
		ret = 1;
		return ret;
	}
	public ABC get2(ABC a3)
	{
		f0 = new ABC();
		a3.f0 = f0;
		return f0;
	}
}

class BCD extends ABC
{	
	BCD f1;
	public int get1()
	{
		int ret;
		ret = 2;
		return ret;
	}
	public ABC get2(ABC a4)
	{
		f1 = new BCD();
		a4.f0 = f1;
		return f1;
	}

}
