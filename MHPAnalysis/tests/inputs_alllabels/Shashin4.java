//base case, no synchronization whatsoever. Guaranteed data race.

class Shashin4 {

	public static void main (String [] args){
		
		try {
			A a;
			B b;
			int zero;
			Data d;
			
			zero = 0;
			d = new Data();
			d.d = zero;
			
			 a= new A();
			 b = new B();
			a.x = d;
			b.x = d;
			
			a.start();
			b.start();
			
			a.join();
			b.join();
			
			
		} catch (Exception ex){
		
		}
	}
}

class Data {
	int d;
}

class A extends Thread {
	
	Data x;
	
	public void run() {
	
		try {
			int t;
			int one;
			one = 1;
			
			t = x.d;
			t = t + one;
	/* L1 : */	x.d = t;
		} catch (Exception ex) { }
		
	}
}

class B extends Thread {
	
	Data x;
	
	public void run() {
	
		try {
			int t;
	/* L2 : */	t = x.d;
			
			System.out.println(t);
		} catch (Exception ex) { }
		
	}
}

//Yes
/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L2 mhp? L2 */
