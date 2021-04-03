class input1
{
	public static void main(String[] args)
	{
		A a;
		A b;
		boolean z;
		a = new A();
		b = new B();
		z = true;
		/*a alias? b*/
		if(z)
			a = new C();
		else
			a = b;
	}
}
class A
{
	int f;
}
class B extends A
{
	int f;	
}
class C extends B
{
	int f;
}
