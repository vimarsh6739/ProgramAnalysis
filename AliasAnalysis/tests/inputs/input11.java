class Main {
	public static void main (String [] args){
		
		A a1;
		A a2;
		int y;
		boolean z;
		
		int a;
		int b;
		
		a = 100;
		b = 200;
		
		z = a < b;
		a1 = new A();
		
		if(z){
			a2 = a1;
		} else {
			a2 = a1.getNullData();
		}
		
		/* a2 alias? a1 */
		
		//also a potential NPE here
		y = a2.getY();
		System.out.println(y);
	}
}

class A{
	int y;
	
	public A getNullData(){
		A obj1;
		return obj1;
	}
	
	public int getY(){
		return y;
	}
}

