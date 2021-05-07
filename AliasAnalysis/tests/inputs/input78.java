class input78{
	public static void main(String[] args){
		int i;
		Test4Aux a;
		Test4Aux b;
		Test4Aux c;
		Test4Aux d;
		Test4Aux e;
		int one;
		int five;
		boolean z;

		d = new Test4Aux();
		e = new Test4Aux();
		a = d;
		c = e;

		one = 1;
		five = 5;
		 i =0;
		 z = i < five;
		while(z){
			b = a;
			a = c;
			/* a alias? b */
			/* a alias? c */
			/* b alias? c */
			c = b;
			i =i +one;
			z = i < five; 
		}

		/* a alias? d */
		/* c alias? e */

		one = 2;
	}
}

class Test4Aux{
	int x;
}