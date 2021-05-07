class Main {

	public static void main (String [] args){
		C c;
		B b;
		A a;
		A tmpObj;
		
		int eleven;
		int twentyone;
		int thirtyone;
		int tmp;
		
		eleven = 11;
		twentyone = 21;
		thirtyone = 31;
		
		c = new C();
		c.data = thirtyone;
		
		b = new B();
		b.data = twentyone;
		c.obj = b;
		
		a = c.foo();
		/* a alias? b */
		tmp = b.data;
		System.out.println(tmp);
		tmp = a.data;
		System.out.println(tmp);
		
		a.obj = a;
		tmpObj = a.obj;
		/* tmpObj alias? b */
		/* a alias? b */
		tmp = tmpObj.data;
		System.out.println(tmp);
		
		a.obj = c;
		/* c alias? a */
		tmp = c.data;
		System.out.println(tmp);
	}
}

class A {
	A obj;
	int data;
	
	public A foo(){
		
		return obj;
	}
}

class B extends A {
	
}

class C extends B {
	
	public A foo(){
		
		return obj;
	}
}
