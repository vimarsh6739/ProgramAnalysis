class input67{

    public static void main(String[] args)
    {
    	A a;
    	B b;
    	A c;
    	A d;
    	A e;
    	int f;
    	a = new A();
    	b = new B();
    	c = new A();
    	d = new A();
    	e = c.a2;
    	/*d alias? d*/c.a2 = d;
    	a.a2 = c;
    	a.a2 = d;
    	f =  a.abc(a);
    	/*a alias? b*/e = a.a2;
    	
    	/*e  alias? b*/e = a.a2;
    }

}

class A
{
	int a1;
	A a2;
	public int foo(B b)
	{
		int x;		
		int y;
		int z;
		y = b.b1;
		z = b.b2;
		x = y + z;
		z = b.b3; 
		x = x - z;
		return x; 
	}

	public int abc(A a)
	{
		int x;
		int y;
		int one ;
		one = 1;
		y = a.a1;
		x = y + one;
		a.a1 = x;
		return x;
	}
}

class B
{
	int b1;
	int b2;
	int b3;
	A b4;

	public int bar(A a)
	{
		int y;
		y = a.a1;
		return y;
	}
}
