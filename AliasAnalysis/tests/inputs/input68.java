class input68{ 
	public static void main (String[] args){ 
		int  temp0;boolean rider;
		Braces a;
		Braces b;
		Braces c;
		Braces x;
		Braces y;
		Braces w;
		rider = true;
		if(rider) { 
			a = new Braces();
			b = new Braces();
			c = a;
			x = a.z;
			y = b.z;
			w = c.z;
		}
		else
		{
			a = new Braces();
			c = new Braces();
			b = a;
			x = a.z;
			y = b.z;
			w = c.z;
		}
		/* x alias? w */
		/* x alias? y */
		/* a alias? c */
		temp0 =new Braces().compute();
		System.out.println(temp0);
	}
}

class Braces {
	Braces z;
	public int compute() {
		z = 10;
		return z;
	}
}



