// models a deadlock
class Shashin5 {

	public static void main (String [] args){
		
		try {
			A a;
			B b;
			int zero;
			Lock1 l;
			
			zero = 0;
			l = new Lock1();
			l.d = zero;
			
			a = new A();
			b = new B();
			a.l1 = l;
			b.l1 = l;
			
			a.start();
			b.start();
			
			a.join();
			b.join();
			
		} catch (Exception ex) {
		
		}
	}
}

class Lock1 {
	int d;
}

class Lock2 {
	int d;
}


class A extends Thread {
	
	Lock1 l1;
	Lock2 l2;
	
	public void run () {
	
		try {
			int t;
			synchronized(l1) {
	/* L1 : */		t = l1.d;
				
				synchronized(l2) {
	/* L2 : */			l2.d = t;
				}
			
			}
		} catch (Exception ex) {
		
		}
		
	}
	
}

class B extends Thread {
	
	Lock1 l1;
	Lock2 l2;
	
	public void run () {
	
		try {
			int t;
			synchronized(l2) {
	/* L3 : */		t = l2.d;
				
				synchronized(l1) {
	/* L4 : */			l1.d = t;
				}
			
			}
		} catch (Exception ex) {
		
		}
		
	}
	
}

/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L1 mhp? L4 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L4 mhp? L4 */