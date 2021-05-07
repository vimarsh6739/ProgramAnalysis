class Main {
	public static void main (String [] args) {
		A a;
		B b;
		A c;
		B d;
		A e;
		B f;
		int y;

		a = new A(); 
		b = new B();
		a.f0 = b;
		b.f0 = a;
		c = b.f0;
		d = a.f0;
		
		e = d.f0;
		f = c.f0;
		/* a alias? b */
		/* a alias? c */
		/* e alias? f */
		/* e alias? a */
		/* d alias? f */
		y = 1;
		System.out.println(y);
	}
}

class A {
	B f0;
}

class B  {
	A f0;
}