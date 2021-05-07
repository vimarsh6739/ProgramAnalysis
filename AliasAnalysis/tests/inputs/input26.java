class input4{
	public static void main(String[] args){
		A a1;
		A a2;
		B b1;
		B b2;
		boolean cond;
		int i;
		int j;
		int k;
		
		i=1;
		k=1;
		j = 0;
		a1 = new A();
		a2 = new A();
		cond = j<i;
		while(cond){
			a1 = a2;
			i = i-k;
			cond = j<i;
		}	
		
		/* a1 alias? a2 */
		i=0;
	}
}

class A{
	B f;
}

class B{
	int d;
}

