
class Test2 {
	public static void main(String []args){
		try {
			C2_1 obj;
			C2_1 obj2;
			C2_2 t2;
			int c;
			obj = new C2_1();
			t2 = new C2_2();
			obj2 = new C2_1();
			t2.obj = obj;
			t2.obj2 = obj2;
			/*L1:*/ obj.start();
			/* L2:*/ t2.start();
			c = 5;
			synchronized(obj) {
				/*L3:*/ obj.wait();
				c = 7;
			}
			t2.join();
			/*L4:*/ c = 8;
			obj.join();
			
		} catch (Exception e){}
	}
}
class C2_1 extends Thread{
	C2_1 obj;
	public void run() {
		try {
			int i;
			obj = this;
			i = 10;
			synchronized(obj) {
				/*L5:*/ System.out.println(i);
				/*L6:*/ obj.wait();
				/*L7:*/ i = i*i;
				/*L8:*/i = i-i;
			}
			
		}catch (Exception e){}
	}
}
class C2_2 extends Thread{
	C2_1 obj2;
	C2_1 obj;
	public void run() {
		try {
			int a1;
			int a2;
			boolean b1;
			synchronized(obj) {
				/*L9:*/ obj.notifyAll();
				b1 = true;
			}
			synchronized(obj2) {
				a1 = 4;
				/*L10:*/ a2 = 2;
				b1 = a2 < a1;
				while(b1) {
					a1 = a2;
					b1 = false;
				}
				
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
/* L1 mhp? L9 */
/* L1 mhp? L10 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L2 mhp? L5 */
/* L2 mhp? L6 */
/* L2 mhp? L7 */
/* L2 mhp? L8 */
/* L2 mhp? L9 */
/* L2 mhp? L10 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L3 mhp? L5 */
/* L3 mhp? L6 */
/* L3 mhp? L7 */
/* L3 mhp? L8 */
/* L3 mhp? L9 */
/* L3 mhp? L10 */
/* L4 mhp? L4 */
/* L4 mhp? L5 */
/* L4 mhp? L6 */
/* L4 mhp? L7 */
/* L4 mhp? L8 */
/* L4 mhp? L9 */
/* L4 mhp? L10 */
/* L5 mhp? L5 */
/* L5 mhp? L6 */
/* L5 mhp? L7 */
/* L5 mhp? L8 */
/* L5 mhp? L9 */
/* L5 mhp? L10 */
/* L6 mhp? L6 */
/* L6 mhp? L7 */
/* L6 mhp? L8 */
/* L6 mhp? L9 */
/* L6 mhp? L10 */
/* L7 mhp? L7 */
/* L7 mhp? L8 */
/* L7 mhp? L9 */
/* L7 mhp? L10 */
/* L8 mhp? L8 */
/* L8 mhp? L9 */
/* L8 mhp? L10 */
/* L9 mhp? L9 */
/* L9 mhp? L10 */
/* L10 mhp? L10 */
