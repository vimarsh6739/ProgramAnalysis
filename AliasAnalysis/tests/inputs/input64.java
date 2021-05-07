class input64 {
    public static void main(String[] args) {
		int x;
		int n;
		int si;
		Arr b;
		Awe c;
		Arr d;
		
		c = new Awe();
		si = 6;
		c.oper = si;
		b = new Arr();
		d = new Arr();
		n = b.web(c);
		
		d = new Arr();
		c.serv = d;
		
		System.out.println(n);
	}
}

class Arr {
    int a1;
    int a2;
    Awe r;
    Arr self;
    public int web(Awe h){
        
        int m;
        int n;
        int six;
        boolean t1;
        boolean t2;
        Awe k;
        Arr l;
        Arr temp;
        Awe temp2;
        Arr temp3;
        Arr w;
        Awe x;
        temp2 = new Awe();
        
        n = h.oper;
        six = 6;
        t1 = n < six;
        temp = this;
        
        k = temp.r;
        l= temp.self;
        l = temp2;
        temp3 = temp;
        w = temp3.self;
        x = temp3.r;
        /* l alias? temp2 */
        /* l alias? w */
		/* k alias? x */
        if(t1){
         m = 1;
        } else {
         m = 0;
        }
        return m;
    }
}

class Awe extends Arr {
    Arr serv;
    int oper;
}
