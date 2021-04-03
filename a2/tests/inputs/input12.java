class input5{
	public static void main(String[] args){
		A a1;
		A a2;
		B b;
		int five;
		int ten;
		int twenty;
		
		five = 5;
		ten = 10;
		twenty = 20;
		
		a1 = new A();
		b = new B();
		a1.n = five;
		b.x = a1;
		a2 = b.x;
		a2.n = ten;
		/* a1 alias? b */
		/* a1 alias? a2 */
		
		a1.n = twenty;
	}
}

class A{
	int n;
}

class B{
	A x;
}
