class input79{
	public static void main(String[] args){

		Test5Aux x1;
		Test5Aux x2;
		Test5Aux x3;
		Test5Aux y;
		boolean cond;
		x1 = new Test5Aux();
		x2 = x1;
		cond = true;
		if(cond){
			x2 = new Test5Aux();
			/*x1 alias? x2*/
			System.out.println(cond);
		}
		else{
			x3 = new Test5Aux();
			x3.p = x1;
			y = x3.p;
			/* x2 alias? y */
			System.out.println(cond);
		}

		/* x1 alias? x3 */
		cond = false;

	}
}

class Test5Aux{
	Test5Aux p;
}
