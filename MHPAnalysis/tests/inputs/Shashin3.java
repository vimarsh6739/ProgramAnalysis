//an example of a wasted notify. Also verifies that there are no notify edges

class Shashin3 {

	public static void main (String [] args){
		
		try {
			A a;
			B b;
			Lock l;
			int zero;
			
			zero = 0;
			l = new Lock();
			l.data = zero;
			
			a = new A();
			a.l = l;
			
			b = new B();
			b.l = l;
			
			a.start();
			a.join();
			
			b.start();
			b.join();
			
		} catch (Exception ex){
		
		}
	}
}

class Lock {
	int data;
}

class A extends Thread {

	Lock l;
	
	public void run () {
	
		try {
			int t;
			t = 99;
		
			synchronized(l) {
		/* S0 : */	l.data = t;
				
				l.notify();
			}
			
		} catch (Exception ex) {}
		
	}
}


class B extends Thread {

	Lock l;
	
	public void run () {
	
		try {
			int t;
		
			synchronized(l) {
				l.wait();
				
		/* S1 : */	t = l.data;
				System.out.println(t);
			}
			
		} catch (Exception ex) { }
		
	}
}

//No
/* S0 mhp? S1 */
