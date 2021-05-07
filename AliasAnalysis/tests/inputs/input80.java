class input80{
	public static void main(String[] args){

	}
}

class Test6Aux2{
	Test6Aux p;
	public Test6Aux colgate(){
		Test6Aux x1;
		Test6Aux x2;
		Test6Aux x3;

		x1 = new Test6Aux();
		x3 = new Test6Aux();
		x1 = x1.func();

		/*x2 alias? x1*/
		/*x2 alias? x3*/
		System.out.println(p);
		return x1;
	}
}

class Test6Aux{
	Test6Aux p;
	public Test6Aux func(){
		return p;
	}
}
