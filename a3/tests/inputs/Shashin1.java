class Shashin1 {

	public static void main (String [] args){
		
		try {
			A t1;
			A t2;
			LockedInt li;
			
			t1 = new A();
			t2 = new A();
			li = new LockedInt();
			
			t1.x = li;
			t2.x = li;
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();
			
		} catch (Exception ex){
		
		}
		
		
	}
}

class LockedInt {
	int data;
}

class A extends Thread {
	LockedInt x;
	
	public void run(){
	
		try {
			int y;
			int one;
			/* S5 : */ one = 1;
	    /* S0 : */ synchronized(x){
				/* S1 : */ y = x.data;
				/* S2 : */ y = y + one;
				/* S3 : */ x.data = y;	
			}
		} catch (Exception ex) {

		}
	}
}

/* S0 mhp? S0 */
/* S1 mhp? S1 */ 
/* S2 mhp? S2 */ 
/* S3 mhp? S3*/
  

