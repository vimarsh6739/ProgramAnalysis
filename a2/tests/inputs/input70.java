class input70 {
 
    public static void main(String [ ] args){
         
        int number;
        int count;
        int sum; 
        boolean t1;
        boolean t2;
        boolean t3;
        int t7;
        int t8;
        int t9;
        int t10;
        Test13 a;
        Test13 b;
        Test13 d;
        Test13 c;
        Test13 e;
        Test13 f;
        number=2;
        count=1000;
        sum=0;
        t7 = 0;
        
        t1=t7 <count;
        while( t1){
        	t2=new Test13().isPrimeNumber(number);
            if(t2){
            	t8=1;
                sum=sum+ number;
                count=count-t8;
            }
            else{
            	
            	t3=t1;
            	
            	t3=t2;
            	
            }
            
            t9=1;
            number=number+t9;
            t10=0;
            t1=t10<count;
        }
        d=new Test13();
        c=d;
        t3=true;
        a = new Test13();
		b = new Test13();
		a.f = b;
		e = a.f;
		f = b;
		/* d alias? c */
		/* e alias? c */
		/* e alias? d */
		/* f alias? e */
        
        System.out.println(sum);
    }
}

class Test13 extends input70{
	Test13 f;
    public boolean isPrimeNumber(int number){
         int i;
         boolean t3;
         int t4;
         boolean t5;
         int t11;
         int t12;
         int t13;
         int t14;
         boolean t16;
         t16=true;
         i=2;
         t3=i<number;
         i= 2;
        while(t3){
        	t11=1000;
        	t5=t11<i;
            if(t5){
                t16=false;
            }
            else{
            t13=2;
            t12=number-t13;
            t3=i<t12;
            t14=1;
            i=i+t14;
            t16=true;
            i = i;
            }
        }
        return t16;
    }
}

