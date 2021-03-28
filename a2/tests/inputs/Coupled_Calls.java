class Coupled_Calls
{
	public static void main(String[] arg_arg)
	{
		A a;
		int temp;
		boolean z;
		a = new A();
		temp = 10;
		a.t = temp;
		temp1 = 15;
		a.t1 = temp;
		z = a.foo();
	}
}
class A
{
	A f;
	int t;
	int t1;
	public boolean foo()
	{
		boolean z;
		A obj;
		A obj1;
		z = t < t1;
		if(z)
		{
			obj = new A();
			f = obj;
			z = obj.bar();
		}
		else
		{
			obj = this;
		}
		/*obj alias? obj1*/
		obj1 = obj.f;
		/*f alias? obj1*/
		obj1 = obj.f;
		return z;
		
	}
	public boolean bar()
	{
		boolean z;
		A temp_obj;
		t = t+t1;
		t1 = t-t1;
		/*temp_obj alias? f*/
		t = t-t1;
		temp_obj = this;
		temp_obj = temp_obj.f;
		z = temp_obj.foo();
		return z;		
	}
}
