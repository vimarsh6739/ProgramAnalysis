class Main{
	public static void main (String [] args) {
	
		int x;
		int zero;
		int one;
		int t;
		boolean y;
		A a1;
		A a2;
		A a3;
		A a4;
		A a5;
		
		
		x = 6;
		zero = 0;
		one = 1;
		y = zero < x;
		
		a1 = new A();
		a2 = new A();
		a3 = new A();
		a4 = new A();
		a5 = new A();
		
		
		while(y) {
			a5 = a4;
			a4 = a3;
			a3 = a2;
			a2 = a1;
			
			x = x - one;
			y = zero < x;
		}
		
		/* a2 alias? a1 */
		/* a3 alias? a1 */
		/* a4 alias? a1 */
		/* a5 alias? a1 */
		
		t = 0;

	}
}


class A {
	
}
