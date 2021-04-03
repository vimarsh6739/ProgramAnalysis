class Main {
	
	public static void main (String [] args) {
		A a1;
		A a2;
		int ninety_seven;
		int seventy_seven;
		int x;
		
		ninety_seven = 97;
		seventy_seven = 77;
		a1 = new A();
		a1.data = ninety_seven;
		
		a2 = new A();
		a2.data = seventy_seven;
		x = a2.data;
		System.out.println(x);
		
		a2 = a1.getObject();
		x = a2.data;
		System.out.println(x);
		
		/* a2 alias? a1 */
		
		x = 0;
	}
	
}

class A {
	int data;
	
	public A getObject(){
		A tmp;
		tmp = this;
		
		return tmp;
	}
}
