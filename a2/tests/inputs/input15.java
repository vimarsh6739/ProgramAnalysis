class Main {
	public static void main (String [] args) {
		
		A a0;
		A a1;
		A a2;
		B b0;
		B tmp0;
		B tmp1;
		
		int x;
		int zero;
		boolean y;
		
		x = 100;
		zero = 0;
		y = zero < x;
		
		a0 = new A();
		a1 = new A();
		
		if(y){
			a2 = a0;
		} else {
			a2 = a1;
		}
		
		/* a2 alias? a1 */
		/* a2 alias? a0 */
		
		b0 = new B();
		a2.f = b0;
		
		tmp0 = a0.f;
		tmp1 = a1.f;
		/* tmp0 alias? b0 */
		/* tmp1 alias? b0 */
		
		x = 0;
		
	}
	
}

class A {
	B f;
}

class B {

}
