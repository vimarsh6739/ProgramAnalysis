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
			/* L1 : */ one = 1;
	    /* L2 : */ synchronized(x){
				/* L3 : */ y = x.data;
				/* L4 : */ y = y + one;
				/* L5 : */ x.data = y;	
			}
		} catch (Exception ex) {

		}
	}
}

/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L1 mhp? L4 */
/* L1 mhp? L5 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L2 mhp? L5 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L3 mhp? L5 */
/* L4 mhp? L4 */
/* L4 mhp? L5 */
/* L5 mhp? L5 */
