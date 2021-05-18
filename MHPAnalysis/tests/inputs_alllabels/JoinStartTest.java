class JoinStartTest {
public static void main(String[] a)
{
    try{
        A a1;
        A a2;
        A a3;
        B b;
        int x;
        int one;
        int ten;
        boolean z;
        one = 1;
        ten = 10;
        a1 = new A();
        b = new B();
        a1.b = b;
        z =  true;
        a1.start();
        while(z)
        {
            /*L1:*/x = x+one;
            a1.join();
            a2 = new A();
            a3 = new A();
            a3.b = b;
            a2.b = b;
            a2.start();
            a3.start();
            /*L2:*/z = x < ten;
        }
        a2.join();
        a3.join();
    }
    catch(Exception e){}
}    
}
class A extends Thread
{
    B b;
    public void run()
    {
        try
        {
            int x;
            int y;
            boolean z;
            int t;
            int ten;
            /*L3:*/ten = 10;
            /*L4:*/x = 0;
            y = 1;
            z = true;
            while(z)
            {
                /*L5:*/x = x + y;
                z = x < ten;
                synchronized(b)
                {
                    t = b.z1;
                    t = x+t;
                   /*L6:*/b.z1 =t;
                }
            }

        }
        catch(Exception e){}
    }
}
class B
{
    int z1;
}

/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L1 mhp? L4 */
/* L1 mhp? L5 */
/* L1 mhp? L6 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L2 mhp? L5 */
/* L2 mhp? L6 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L3 mhp? L5 */
/* L3 mhp? L6 */
/* L4 mhp? L4 */
/* L4 mhp? L5 */
/* L4 mhp? L6 */
/* L5 mhp? L5 */
/* L5 mhp? L6 */
/* L6 mhp? L6 */
