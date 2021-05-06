class Main {
	public static void main(String[] args) {
		try {
			A a;
			int x;
			MyThread thread;
			x = 10;
			a = new A();
			thread = new MyThread();
			/* L0: */
			synchronized(a) {
				/* L1: */
				thread.a = a;
				thread.start();
				/* L2: */
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
			/* L3: */
			synchronized(a) {
				/* L4: */
				a.value = y;
				System.out.println(y);
			}
			
		}
		catch (Exception e) {}
	}
}

/* L0 mhp? L3 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
