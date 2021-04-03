class Main {
	public static void main(String[] Args) {
		Object a;
		Object b;
		Object c;
		int y;
		
		a = new Object();
		b = a;
		c = new Object();
		a = c;
		
		/* a alias? b */
		/* b alias? c */
		/* a alias? c */
		
		y = 2;
		System.out.println(y);
	}
}
class Object{}

