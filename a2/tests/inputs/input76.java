class input76{
	public static void main(String[] args){
		int p;
		Test2Aux a1;
		Test2Aux a2;
		Test2Aux a3;
		Test2Aux a4;
		Test2Aux a5;
		Test2Aux a6;
		int temp;
		int ten;
		int i;
		boolean cond;

		a5 = new Test2Aux();
		a6 = new Test2Aux();

		a1 = a5;
		a2 = a6;

		i = 0;
		ten = 10;
		temp =  10;
		cond = i < temp;
		while(cond){
			a3 = a1;
			a1 = a2;

			/*a3 alias? a1*/
			/*a1 alias? a2*/
			/*a3 alias? a2*/

			a2 = a3;
			temp = 1;
			i = i + temp;
			cond = i < ten;
		}

		/* a1 alias? a5 */
		/* a2 alias? a6 */

		i = i + temp;

	}
}

class Test2Aux{
	int x;
}
