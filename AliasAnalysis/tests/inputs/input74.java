class input74 {
	public static void main (String [] args) {
		TA09A a;
		TA09A b;
		TA09A c;
		TA09A d;
		TA09A e;
		TA09A f;
		boolean cond;
		int val;
		cond = false;
		val = 1;
		b = new TA09A();
		if (cond) {
			a = new TA09A();
			a.f = b;
			c = a.f;
			e = b;
			/* e alias? c */ System.out.println(val);
		} else {
			a = new TA09A();
			a.f = b;
			d = a.f;
			f = b;
			/* f alias? d */ System.out.println(val);
		}
		b = new TA09A();
		a.f = b;
		e = a.f;
		f = b;
		/* d alias? c */ System.out.println(val);
		/* e alias? c */ System.out.println(val);
		/* e alias? d */ System.out.println(val);
		/* f alias? c */ System.out.println(val);
		/* f alias? d */ System.out.println(val);

		a = new TA09A();
		b = new TA09A();
		a.f = b;
		e = a.f;
		f = b;
		/* d alias? c */ System.out.println(val);
		/* e alias? c */ System.out.println(val);
		/* e alias? d */ System.out.println(val);
		/* f alias? c */ System.out.println(val);
		/* f alias? e */ System.out.println(val);
	}
}
class TA09A {
	TA09A f;	
}
