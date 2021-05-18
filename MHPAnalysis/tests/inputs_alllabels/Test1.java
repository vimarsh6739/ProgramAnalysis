
class Test1 {
	public static void main(String []args){
		try {
			C1_3 obj;
			C1_1 t1;
			C1_2 t2;
			int a;
			int a1;
			int x;
			obj = new C1_3();
			t1 = new C1_1();
			t2 = new C1_2();
			t1.obj = obj;
			t2.obj = obj;
			t1.start();
			a = 10;
			a1 = 11;
			synchronized(obj) {
				/* L1: */ obj.wait();
				/* L2: */ x = a + a1;
			}
			t1.join();
			t2.start();
			synchronized(obj) {
				/* L3: */ obj.wait();
				/* L4: */ x = a * a1;
			}
			t2.join();
			
		}catch (Exception e){}
	}
}
class C1_3{
	int i;
}
class C1_1 extends Thread{
	C1_3 obj;
	public void run(){
		try {
			boolean b;
			boolean b1;
			boolean b2;
			synchronized(obj) {
				b = true;
				b1 = true;
				b2 = false;
				/* L5: */ b = b1 && b2;
				obj.notifyAll();
				/* L6: */ b = false;				
			}
			
		}catch (Exception e){}
	}
}
class C1_2 extends Thread{
	C1_3 obj;
public void run(){
		try {
			boolean bl;
			int y;
			synchronized(obj) {
				/*L7:*/ bl = true;
				obj.notify();
				if(bl)
					y = 10;
				else
					y = 9;
				/* L8: */ y = y + y;
				
			}
		}catch (Exception e){}
	}
}
/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L1 mhp? L4 */
/* L1 mhp? L5 */
/* L1 mhp? L6 */
/* L1 mhp? L7 */
/* L1 mhp? L8 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L2 mhp? L5 */
/* L2 mhp? L6 */
/* L2 mhp? L7 */
/* L2 mhp? L8 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L3 mhp? L5 */
/* L3 mhp? L6 */
/* L3 mhp? L7 */
/* L3 mhp? L8 */
/* L4 mhp? L4 */
/* L4 mhp? L5 */
/* L4 mhp? L6 */
/* L4 mhp? L7 */
/* L4 mhp? L8 */
/* L5 mhp? L5 */
/* L5 mhp? L6 */
/* L5 mhp? L7 */
/* L5 mhp? L8 */
/* L6 mhp? L6 */
/* L6 mhp? L7 */
/* L6 mhp? L8 */
/* L7 mhp? L7 */
/* L7 mhp? L8 */
/* L8 mhp? L8 */
