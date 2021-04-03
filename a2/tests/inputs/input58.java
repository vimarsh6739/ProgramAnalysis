class Main {
	public static void main(String[] Args) {
		A a;
		B b;
		B c;
		A d;
		int x;
		int y;
	
		b = new B();
		
		x = b.method1(b);
		a = b.f0;
		
		x = b.method2();
		c = b.f1;
		
		// a = new A();
		x = a.method1(c);
		d = a.f0;
		
		/* a alias? c */
		/* a alias? d */
		
		y = 4;
		System.out.println(y);	
	}
}

class A {
	A f0;
	
	public int method1(A o) {
		int ret;
		ret = 0;
		
		f0 = new A();
		o.f0 = f0;
		
		return ret;
	}
}

class B extends A {
	B f1;
	
	public int method1(A o) {
		int ret;
		ret = 0;
		
		f1 = new B();
		o.f0 = f1;
		
		return ret;
	}
	
	public int method2() {
		int ret;
		ret = 0;
		
		f1 = new B();
		
		return ret;
	}
}
