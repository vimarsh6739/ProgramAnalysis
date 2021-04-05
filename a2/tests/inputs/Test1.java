class Test1 {
	public static void main(String[] args) {
		C a1;
		C a2;
		C a3;
		C a4;
		Test1 a5;
		Test1 t1;
		Test1 t2;
		int[] a;
		int[] b;
		int c;
		boolean b1;
		boolean b2;
		boolean t;
		c = 10;
		a = new int[c];
		b = new int[c];
		a1 = new C();
		a2 = new C();
		a4 = new C();
		a5 = a1.m1();
		a2.f0 = a5;
		b1 = true;
		b2 = false;
		/*a2 alias? a1 */
		/*a alias? b*/
		a = b;
		t = b1 && b2;
		while(t) {
			a3 = a1;
			a3 = a2;
			
		}
		/*t1 alias? t2*/
		if(t)
			t1 = a1.m1();
		else
			t2 = a2.m1();
		
				
	}

}
class C{
	Test1 f0;
	public Test1 m1() {
		Test1 t;
		t = new Test1();
		f0 = t;
		return f0;
	}
}
