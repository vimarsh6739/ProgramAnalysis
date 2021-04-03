class Main {
	public static void main(String[] Args) {
		A a;
		A b;
		A c;
		A d;
		int x;
		int y;
		
		a = new A();
		b = a;
		
		b = new A();
		
		x = a.method1(b);
		
		c = a.f0;
		d = b.f0;
		
		/* a alias? b */
		// Yes
		/* c alias? d */
		// Yes
		/* c alias? a */
		// No
		
		y = 3;
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
