class Main {

	public static void main (String [] args){
		A a0;
		A a1;
		A a2;
		B b0;
		B b1;
		B tmpObjB0;
		B tmpObjB1;
		int tmpInt;
		
		a0 = new A();
		b0 = new B();
		
		a1 = new A();
		a0 = a1;
		a1.f = b0;
		tmpObjB0 = a0.f;
		/* tmpObjB0 alias? b0 */
		
		b1 = new B();
		a0.f = b1;
		
		tmpObjB0 = a0.f;
		tmpObjB1 = a1.f;
		/* tmpObjB0 alias? tmpObjB1 */
		
		a2 = new A();
		tmpInt = a0.modify(a2);
		tmpObjB0 = a1.f;
		tmpObjB1 = a2.f;
		/* tmpObjB0 alias? tmpObjB1 */
		/* a2 alias? a1 */
		
		tmpInt = 0;
	}
	
}

class A {
	B f;
	
	public int modify(A a){
		int x;
		x = 1;
		a.f = f;
		return x;
	}
	
}

class B {

}
