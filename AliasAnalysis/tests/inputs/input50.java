class input3{
	public static void main(String[] args){
		A p;
		A q;
		int one;
		int i;
		int max;
		boolean flag;
		
		p = new A();
		q = p;
		/* p alias? q */
		
		one = 1;
		i = 0;
		max = 5;
		flag = i < max;
		while(flag){
		
			/* p alias? q */
			q = new A();
			/* p alias? q */
			
			i = i + one;
			flag = i < max;
		}
	}
}

class A{
	int x;
	int y;
}
