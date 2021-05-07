class Main {
	public static void main(String[] args) {
		try {
			CommonClass1 c1;
			CommonClass2 c2;
			MyThread1 t1;
			MyThread2 t2;

			c1 = new CommonClass1();
			c2 = new CommonClass2();
			t1 = new MyThread1();
			t2 = new MyThread2();

			//assign the classes 
			t1.c1 = c1;
			t1.c2 = c2;

			t2.c1 = c1;
			t2.c2 = c2;	

			//start the threads and wait for them to finish 
			t1.start();
			t2.start();
			t1.join();
			t2.join();

		} 
		catch(Exception e) {}
	}
}

class CommonClass1 {
	int value;
}

class CommonClass2 {
	int value;
}

class MyThread1 extends Thread {
	CommonClass1 c1;
	CommonClass2 c2;

	public void run() {
		try {
			int y;
			int z1;
			y = 20;
			z1 = 10;
			synchronized(c1) {
				/* L1: */
				c1.value = y;
				synchronized(c2) {
					/* L2: */
					c2.value = z1;
				}
			}
			/* L3: */
			System.out.println(y);
		} 
		catch(Exception e) {}
	}
}

class MyThread2 extends Thread {
	CommonClass1 c1;
	CommonClass2 c2;

	public void run() {
		try {
			int x;
			int z2;
			x = 10;
			z2 = 20;
			synchronized(c2) {
				/* L4: */
				c2.value = x;
				synchronized(c1) {
					/* L5: */
					c1.value = z2;
				}
			}
			/* L6: */
			System.out.println(x);
		} 
		catch(Exception e) {}
	}
}

/* L1 mhp? L4 */
/* L2 mhp? L4 */
/* L5 mhp? L1 */
/* L3 mhp? L6 */
