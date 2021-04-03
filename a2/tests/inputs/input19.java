class input4{
	public static void main(String[] args){
		A p;
		A q;
		int five;
		int ten;
		int twenty;
		
		five = 5;
		ten = 10;
		twenty = 20;
		
		p = new A();
		q = new A();
		p.x = five;
		q.x = five;
		/* p alias? q */
		
		q = p;
		p.x = ten;
		q.x = twenty;
		/* p alias? q */
		
		p.x = five;
		q.x = five;
	}
}

class A{
	int x;
}
