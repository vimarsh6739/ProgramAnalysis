class Main {
	public static void main (String [] args) {
		
		int y;
		A a1;
		A a2;
		B b1;
		B b2;
		C c1;
		C c2;
		A q1;
		B q2;
		B q3;
		B q4;
		boolean x;
		a1 = new A(); 
		b1 = new B(); 
		c1 = new C(); 

		a1.f0 = c1;
		b1.f0 = c1;
		b1.f1 = b1;
		c1.f0 = c1;
		c1.f1 = b1;
		c1.f2 = a1;

		y = 1;
		x = true;
		if(x) {
			c2 = new C(); 
			a2 = a1;
			a2.f0 = c2;
			b2 = new B(); 

		}
		else {
			a2 = new A(); 
			a2.f0 = c1;
			b2 = new B(); 

		}

		c2 = new C(); 
		c2.f1 = b2; 

		q1 = a2.f0;
		q2 = q1.f0;
		/* q1 alias? q2*/
		/* q1 alias? c2*/
		/* q4 alias? a1*/
		/* q2 alias? c1*/
		/* a2 alias? q2*/
		System.out.println(y);
	}
}

class A {
	C f0;
}

class B extends A{
	B f1;
}

class C extends  B {
	A f2;
}

