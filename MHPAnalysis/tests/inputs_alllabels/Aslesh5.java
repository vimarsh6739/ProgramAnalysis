class Main {
	public static void main(String[] args) {
		try {
			int x;
			A a;
			MyThread1 t1;
			MyThread2 t2;
			boolean bool;

			x = 0;
			bool = true;
			a = new A();
			a.flag = bool;
			t1 = new MyThread1();
			t2 = new MyThread2();
			t1.a = a;
			t2.a = a;

			t1.start();
			t2.start();
			t1.join();
			t2.join();

			x = 10;
			System.out.println(x);


		} catch (Exception e) {}
	}
}

class A {
	boolean flag;
}

class MyThread1 extends Thread {
	A a;

	public void run() {
		try {
			int y;
			boolean bool1;
			/* L1: */
			y = 1;
			synchronized(a) {
				/* L2: */
				a.wait();
				bool1 = true;
				while(bool1) {
					/* L3: */
					bool1 = a.flag;
				}
			}
		}
		catch (Exception e) {}
	}
}

class MyThread2 extends Thread {
	A a;

	public void run() {
		try {
			int z;
			boolean bool2;
			/* L4: */
			z = 2;

			synchronized(a) {
				/* L5: */
				a.notifyAll();
				bool2 = false;
				//set some flag
				a.flag = bool2; 
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
