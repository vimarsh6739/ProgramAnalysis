class Main {
	public static void main (String [] args) {
		A a1;
		A a2;
		A a3;
		A a4;
		B b1;
		B b2;
		B b3;
		A b4;
		int y;
		
		a1 = new A();
		a2 = new A();
		b1 = new B();
		/* a1 alias? b2 */
		a1.f0 = a2;
		a2.f0 = a1;
		b1.f0 = a2;
		b1.f1 = b1;
		b2 = new B();
		a1.f0 = b2;
		a3 = a1.f0;
		a3.f0 = b2;
		b2.f1 = b1;
		b3 = b2.f1;
		b3.f0 = a1;
		b3.f1 = b1;
		

		b4 = b3.f0;
		/* b4 alias? a3 */
		/* a1 alias? a3 */
		/* b3 alias? b4*/

		y = 1;
		System.out.println(y);
	}
}

class A {
	A f0;
}

class B extends A{
	B f1;
}