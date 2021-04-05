class Test3 {
	public static void main(String[] args) {
		Class1 c1;
		Class2 c2;
		Class2 c5;
		Class3 c3;
		Class4 c4;
		Class1 c6;
		int a;
		int b;
		int d;
		int[] c;
		boolean b1;
		c1 = new Class1();
		c3 = new Class4();
		c2 = new Class2();
		c5 = new Class2();
		a = c1.f0;
		b = c3.f0;
		c = new int[b];
		b = c.length;
		b1 = a < b;
		d = 5;
		d = c2.setGetf1(d);
		d = c1.setGetf0(d);
		if(b1)
			c6 = c2.m1(c5);	
		else
			c6 = c1.m1(c5);
		/*c2 alias? c6*/
		/*c2 alias? c1*/
		/*c6 alias? c1*/
		/*c6 alias? c3*/
		/*c6 alias? c5*/
		c6 = c3.m1(c5);
		
	}
}
class Class1{
	int f0;
	public int setGetf0(int f) {
		f0 = f;
		return f0;
	}
	public Class1 m1(Class2 x2) {
		Class1 t;
		int x;
		t = this;
		x = x2.f1;
		f0 = f0 + x;
		return t;
		
	}
}
class Class2 extends Class1{
	int f1;
	public int setGetf1(int f) {
		f1 = f;
		return f1;
	}
	public Class1 m1(Class2 x2) {
		Class1 t;
		int x;
		x = x2.f1;
		t = new Class1();
		f0 = f0 - x;
		return t;
		
	}
	
}
class Class3 extends Class1{
	int f3;
	public int setGetf3(int f) {
		f3 = f;
		return f3;
	}
	
}
class Class4 extends Class3{
	int f4;
	public int setGetf4(int f) {
		f4 = f;
		return f4;
	}
	public Class1 m1(Class2 x2) {
		int x;
		x = x2.f1;
		f4 = f4 * x;
		return x2;
		
	}
}
