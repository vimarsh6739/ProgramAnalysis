class Main {
	public static void main(String[] Args) {
		A a;
		A b;
		A c;
		A d;
		A e;
		int y;
		
		a = new A();
		b = a;
		
		b = new A();
		
		a.f0 = b;
		c = new A();
		b.f0 = c;
		c.f0 = a;
		
		/* a alias? b */
		// Yes
		
		d = c.f0;
		/* c alias? d */
		// No
		
		/* c alias? a */
		// No
		
		e = a.f0;
		/* e alias? c */
		// No
		
		y = 1;
		System.out.println(y);	
	}
}

class A {
	A f0;
}
