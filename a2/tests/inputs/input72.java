class input72{
	public static void main(String[] args){

		SELCONT09Aux x;
		SELCONT09Aux y;
		SELCONT09Aux z;
		SELCONT09Aux w;
		int temp;
		x = new SELCONT09Aux();
		y = new SELCONT09Aux();
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

class SELCONT09Aux{
	int x;
	public SELCONT09Aux getObj(SELCONT09Aux t){
		return t;
	}

	public SELCONT09Aux getAux(SELCONT09Aux t){
		SELCONT09Aux p;
		p = this;
		return p;
	}
}
