class input3 
{
   public static void main(String [] args)
     {
        int[] y;
        int[] x;
        int a;
        int b;
        int c;
        int d;  
        int i;
        int r;
        a = 0;
        b = 1;
        c = 2;
        d = 4;
        i = 3;
        y = new int[i];//R1
        y[a] = a;
        y[b] = b;
        y[c] = c;
        x = y;//R1
        /*x alias? y*/ 
        x[c] = d;
        r = x[c];
        x = new int[i];//{R1,R2}
        /*x alias? y*/ 
        System.out.println(r);
     }
}


