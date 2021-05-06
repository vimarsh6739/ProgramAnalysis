
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
				/* L5: */ obj.wait();
				/* L6: */ x = a * a1;
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
				/* L3: */ b = b1 && b2;
				obj.notifyAll();
				/* L4: */ b = false;				
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
/*L1 mhp? L3*/
/*L2 mhp? L4*/
/*L1 mhp? L5*/
/*L5 mhp? L7*/
/*L6 mhp? L8*/

