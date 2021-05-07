class input66 {
   public static void main(String [] args) {
	   int t;
	   int j;
	   j = 20;
	   t = new A().A_func(j);
      System.out.println(t);
   }
}

class A {
	int i;
	B objB;
    public int A_func(int num){
    	int j;
    	boolean flag;
    	int val;
    	B tempB;
    	A obj1A;
    	A a1;
    	B b1;
    	val = 1;
    	i = 0;
    	obj1A = new A();
    	a1 = obj1A;
    	b1 = new B();
    	obj1A.objB = b1;
    	flag = true;
    	if (flag) {
			b1 = obj1A.objB;
			tempB = b1;
		}else {
			b1 = new B();
			tempB = b1;
		}
        b1.a1 = a1;
        /* b1 alias? tempB */
        System.out.println(val);
        /* a1 alias? b1 */
        System.out.println(val);
        /* obj1A alias? a1 */
        System.out.println(val);
        return num;
    }
    
    public int A1_func(int num, boolean flag) {
		int n;
		n = num;
		if (flag) {
			n = n + num;
		}else {
			n = num;
		}
		System.out.println(n);
		return num;
	}
}

class B{
	int num;
	A a1;
	public int B_func() {
		int j;
		j = 0;
		return j;
	}
}
