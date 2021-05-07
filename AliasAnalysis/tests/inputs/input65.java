class input65 {
	public static void main(String[] args) {
		C5 c5;
		C5 c5_1;
		int g1;
		boolean b;
		int x;
		int y;
		x = 10;
		y = 20;
		b = x < y;
		if(b){
			c5_1 = new C5_1();
			c5 = new C5();
			/* c5_1 alias? c5 */
			g1 = 10;
		}else{
			c5_1 = new C5();
			c5 = new C5_1();
			/* c5_1 alias? c5 */
			g1 = 20;
		}
		/* c5_1 alias? c5 */
		c5 = new C5();
		/* c5_1 alias? c5 */
		System.out.println(x);
		
	}

}

class C5{
	
}

class C5_1 extends C5{
	
}
