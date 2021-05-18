class NestedRecursion {
    public static void main(String[] agr)
    {
        try{
            A a;
            B b;
            C c;
            D d;
            int y;
            a = new A();
            b = new B();
            c = new C();
            d = new D();
            a.c = c;
            b.c = c;
            y =  0;
            c.x = y;
            a.start();
            b.start();
            d.start();
            a.join();
            b.join();
            d.join();
        }
        catch(InterruptedException e){}
    }    
}
class A extends Thread
{
    C c;
    public void run()
    {
        try{
            D d2;
            int x;
            int one;
            int ten;
            boolean z;
            one = 1;
            ten = 10;
            /*L1:*/synchronized(c){
                x = c.x;
                x = x+one;
                c.x = x;
                c.wait();
                z = x < ten;
            }
            d2 = new B();
            if(z){
                /*L2:*/
                d2.start();
                d2.join();
            }
            else{
                /*L3:*/
                d2.start();
                d2.join();
            }
        }
        catch(Exception e){}
    }
}
class B extends Thread
{
    C c;
    public void run()
    {
        try{
            D a2;
            int x;
            int one;
            int ten;
            boolean z;
            one = 1;
            ten = 10;
            /*L4:*/synchronized(c)
            {
                x = c.x;
                x = x+one;
                c.x = x;
                z = x < ten;
                c.notifyAll();
            }
            if(z)
            {
                /*L5:*/a2 = new D();
                a2.start();
            }
            else{}
        }
        catch(Exception e){}
    }
}
class D extends Thread
{
    public void run()
    {
        try{
            int x;
            int y;
            x = 1;
            y = 2;
            /*L6:*/x = x + y;
        }
        catch(Exception e){}
    }
}
class C
{
    int x;
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
