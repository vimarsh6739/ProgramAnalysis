class Test3{
	public static void main(String []args){
		try {
			B t1;
			C t2;
			Buf b;
			t1 = new B();
			t2 = new C();
			b = new Buf();
			t1.b = b;
			t2.b = b;

			t1.start();
			t2.start();
			t1.join();
			t2.join();
		} catch (Exception e){}
	}	
}

class Buf {
	int x;
}
class B extends Thread {
	Buf b;
	public void run() {
	try {
		int x;
		synchronized (b){
			x = b.x;
		}
/*  L1: */ System.out.println(x);
	} catch (Exception e){}

	}
}

class C extends Thread {
	Buf b;
	public void run() {
	try {
		int y;
		int z;
		y = 3;

/* L2: */ System.out.println(y);
		synchronized (b) {
			z = 1;
			z = y + z;
			b.x = z;
		}

	} catch (Exception e){}
	}
}
/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L2 mhp? L2 */

