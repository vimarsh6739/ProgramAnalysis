class Main {
	public static void main(String[] Args) {
		Object a;
		Object b;
		Object c;
		int y;
		boolean cond;
		
		a = new Object();
		c = new Object();
		
		cond = true;

		if (cond) {
			b = c;
		}
		else {
			b = a;
		}
		/* a alias? b */
		/* b alias? c */
		/* a alias? c */
		
		y = 5;
		System.out.println(y);
	}
}
class Object{}
