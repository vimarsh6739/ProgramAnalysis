
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
				/*L6:*/ System.out.println(i);
				/*L7:*/ obj.wait();
				/*L5:*/ i = i*i;
				/*Added_code:*/i = i-i;
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
				/*L8:*/ obj.notifyAll();
				b1 = true;
			}
			synchronized(obj2) {
				a1 = 4;
				/*L9:*/ a2 = 2;
				b1 = a2 < a1;
				while(b1) {
					a1 = a2;
					b1 = false;
				}
				
			}
		}catch (Exception e){}
	}
}
/*L1 mhp? L2*/
/*L3 mhp? L6*/
/*L3 mhp? L7*/
/*L4 mhp? L8*/
/*L5 mhp? L9*/