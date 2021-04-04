
class input71{
	public static void main(String[] args){
		int p;
		SELCONT08Aux a1;
		SELCONT08Aux a2;
		SELCONT08Aux a3;
		SELCONT08Aux a4;
		SELCONT08Aux a5;
		SELCONT08Aux a6;
		int temp;
		int i;
		int ten;
		boolean cond;

		a5 = new SELCONT08Aux();
		a6 = new SELCONT08Aux();

		a1 = a5;
		a2 = a6;

		i = 0;
		temp =  10;
		ten = 10;
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
		/* a5 alias? a6 */

		i = i + temp;

	}
}

class SELCONT08Aux{
	int x;
}
