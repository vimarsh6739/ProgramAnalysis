class input2{
	public static void main(String[] args){
		A p;
		A q;
		boolean flag;
		
		p = new A();
		q = p;
		/* p alias? q */
		
		flag = false;
		if(flag){
			q = new A();
			/* p alias? q*/
			flag = false;
		}
		else{ 
			/* p alias? q*/
			flag = true;
		}
	}
}

class A{
	int x;
	int y;
}
