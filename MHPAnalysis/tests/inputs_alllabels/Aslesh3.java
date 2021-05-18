class Main {
	public static void main(String[] args) {
		try {
			A a;
			int x;
			MyThread thread;
			x = 10;
			a = new A();
			thread = new MyThread();
			/* L1: */
			synchronized(a) {
				/* L2: */
				thread.a = a;
				thread.start();
				/* L3: */
				a.value = x;
			}
			System.out.println(x);
			thread.join();
		}
		catch (Exception e) {}
		
	}
}

class A {
	int value;
}

class MyThread extends Thread {
	A a;

	public void run() {
		try {
			int y;
			y = 20;
			/* L4: */
			synchronized(a) {
				/* L5: */
				a.value = y;
				System.out.println(y);
			}
			
		}
		catch (Exception e) {}
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

