class input69 {
   public static void main(String [] args) {
	   int t;
	   t = new C().C_func();
      System.out.println(t);
   }
}

class C {
	public int C_func() {
		int num_aux ;
		boolean flag;
		D d1;
		C c1;
		D d2;
		C c2;
		int zero;
		int one;
		one = 1;
		zero = 0;
		num_aux = 0;
		flag = true;
		d1 = new D();
		while(flag) {
			if (flag) {
				d2 = d1.retD1();
			}else {
				d2 = d1;
			}
			flag = false;
		}
		c1 = d1.retC1();
		c2 = c1;
		/* c2 alias? d1 */
		System.out.println(num_aux);
		/* c1 alias? d2 */
		System.out.println(num_aux);
		/* d1 alias? d2 */
		System.out.println(num_aux);
		c2 = new C();
		/* c2 alias? c1 */
		System.out.println(num_aux);
        return num_aux ;
	}
}

class D{
	C c1;
	D d1;
	public C retC1() {
		c1 = new C();
		return c1;
	}
	public D retD1() {
		d1 = new D();
		return d1;
	}
}
