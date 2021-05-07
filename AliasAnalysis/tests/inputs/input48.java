class input1 {

	public static void main(String[] args) {
		A a;
		A b;
		int i;
		A d ;
		
		i=4;
		a = new A();
		
		b=a;
		/* a alias? b */

		i=b.setA(i);
		/* a alias? b */

		
		d= new A();
		i = d.setA(i);
		/* d alias? b */

		System.out.println(i);
	}
}


class K {
	 int i;

	public int set(int j) {
		i = j;
		return i;
	}

	public int get() {
		return i;
	}
}


class A {
	K k;

	public int setA(int i) {
	
		k = new K();
		i=k.set(i);
		return i;
	}

	public int getVal() {
		int i;
		i=k.get();
		return i;
	}
}



