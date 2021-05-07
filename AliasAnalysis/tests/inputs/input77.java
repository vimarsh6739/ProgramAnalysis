class input77{
	public static void main(String[] args){

		Test3Aux x;
		Test3Aux y;
		Test3Aux z;
		Test3Aux w;
		int temp;
		x = new Test3Aux();
		y = new Test3Aux();
		z = y.getObj(x);
		w = y.getAux(x);
		/*x alias? y*/
		/*x alias? z*/
		/*y alias? z*/
		/*y alias? w*/
		/*x alias? w*/
		/*w alias? z*/
		temp = 0;
	}
}

class Test3Aux{
	int x;
	public Test3Aux getObj(Test3Aux t){
		return t;
	}

	public Test3Aux getAux(Test3Aux t){
		Test3Aux p;
		p = this;
		return p;
	}
}