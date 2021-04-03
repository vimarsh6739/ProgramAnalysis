class input5{
	public static void main(String[] args){
		A a;
		A b;
		A c;
		int z;
		a = new A();
		b = new A();
		c = new A();
		c = b;
		/* c alias? b */
		/* c alias? a */
		c = a;
		/* a alias? b */
		z=1;
	}
}
class A{
	int dummy;
}
