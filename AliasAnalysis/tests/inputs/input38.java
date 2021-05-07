class input1{
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
		}
		else{ 
		
		}
		/* p alias? q*/
		flag = true;
	}
}

class A{
	int x;
	int y;
}
