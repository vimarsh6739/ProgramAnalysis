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
			thread.a = a;
			thread.start();
			/* L2: */
			a.value = x;
			System.out.println(x);
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
			a.value = y;
		}
		catch (Exception e) {}
	}
}

/* L1 mhp? L3 */
/* L2 mhp? L3 */
/* L1 mhp? L2 */
