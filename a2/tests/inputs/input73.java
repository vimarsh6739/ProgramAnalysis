class input73{
	public static void main(String[] args){
		int i;
		SELCONT10Aux a;
		SELCONT10Aux b;
		SELCONT10Aux c;
		SELCONT10Aux d;
		SELCONT10Aux e;
		int one;
		int five;
		boolean x;
		x=false;

		d = new SELCONT10Aux();
		e = new SELCONT10Aux();
		a = d;
		c = e;

		one = 1;
		five = 5;
		while(x){
			b = a;
			a = c;
			/* a alias? b */
			/* a alias? c */
			/* b alias? c */
			c = b;
		}

		/* a alias? d */
		/* c alias? e */

		one = 2;
	}
}

class SELCONT10Aux{
	int x;
}
