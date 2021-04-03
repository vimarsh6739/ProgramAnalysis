class input3{
	public static void main(String[] args){
		A a1;
		A a2;
		B b1;
		B b2;
		
		boolean flag;
		flag=true;
		a1 = new A();
		a2 = new A();
		
		b1 = new B();
		a1.f = b1;
		
		if(flag){
			a2 = new A();
			b1 = new B();
			a2.f = b1;
			a1 = a2;
		}else{
			a2 = new A();
			b1 = new B();
			a2.f = b1;
			a1.f = b1;
		}
		b1 = a1.f;
		b2 = a2.f;
		/* a1 alias? a2 */
		/* b1 alias? b2 */
		flag = false;
	}
}

class A{
	B f;
}

class B{
	int d;
}

