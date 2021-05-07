class Shashin2 {

	public static void main (String [] args){
		
		try {
			Writer w1;
			Reader r1;
			Reader r2;
			Lock lock;
			int zero;
			zero = 0;
			
			w1 = new Writer();
			r1 = new Reader();
			r2 = new Reader();
			lock = new Lock();
			lock.data = zero;
			
			w1.l = lock;
			r1.l = lock;
			r2.l = lock;
			w1.start();
			r1.start();
			r2.start();
			r1.join();
			r2.join();
			w1.join();
		} catch (Exception ex){
		
		}
	}
}

class Lock {
	int data;
}

class Writer extends Thread {
	Lock l;
	
	public void run(){
		
		try {
			
			int temp;
			int one;
			int two;
			int count;
			boolean check;
			count = 0;
			one = 1;
			two = 2;
			
			check = count < two;
			
			//the writer should produce two units of data
			while(check) {
				synchronized(l){
					//produce (increment) one unit of data in the lock
					temp = l.data;
					temp = temp + one;
			/* S0 : */	l.data = temp;
					
					//notify all the waiting threads
					l.notifyAll();
				}
				
				count = count + one;
				check = count < two;
			}
		} catch (Exception ex) {
		
		}
	}
	
}

class Reader extends Thread {
	Lock l;
	
	public void run(){
		try {
			boolean noData;
			int zero;
			int one;
			int two;
			int temp;
			/*S2: */zero = 0;
			synchronized(l) {
				
				temp = l.data;
				noData = temp < one;
				while(noData) {
					l.wait();
				}
				
				//decrement (consume) one unit of data
				temp = l.data;
		/*S3:*/	temp = temp - one;
		/* S1 : */	l.data = temp;
			}
			/*S4:*/l.data = temp;
		} catch (Exception ex) {
		
		}
	}
}
/* S0 mhp? S1 */
/* S1 mhp? S1*/
/*S1 mhp? S2*/
/*S0 mhp? S2*/
/*S4 mhp? S1*/
/*S4 mhp? S4*/
/*S0 mhp? S4*/