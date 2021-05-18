class WhileTest
{
    public static void main(String[] args)
    {
        try{
            A a;
            B b;
            C c;
            D d;
            boolean x1;
            int y1;
            int one;
            int ten;
            ten = 10;
            a = new A();
            b = new B();
            c = new C();
            d = new D();
            a.b = b;
            a.c = c;
            a.d = d;
            a.start();
            one = 1;
            y1 = 1;
            x1 = y1 < ten;
            while(x1)
            {
                /*L1:*/y1 = y1 + one;
                synchronized(b)
                {
                    b.v = y1;
                    b.notifyAll();
                    synchronized(d){
                    /*L2:*/c.x2 = y1;
                    }
                }
                /*L3:*/x1 = y1 < ten;
            }
            /*L4:*/a.join();

        }
        catch(Exception e){}
    }
}
class A extends Thread
{
    B b;
    C c;
    D d;
    public void run()
    {
        try{
            int x;
            int y;
            x = 0;
            y = 1;
            /*L5:*/x = x + y;
            synchronized(b)
            {
                b.u = x;
                synchronized(c)
                {
                    c.w = x;
                    c.notifyAll();//no effect on anyone
                    synchronized(d)
                    {
                        /*L6:*/d.d1 = x;
                        d.notify();
                    }
                    /*L7:*/c.x2 = y;
                }
                b.v = x;
                /*L8:*/b.wait();
                /*L9:*/y = b.u;
                x = y + x;
                b.u = x;
            }
        }
        catch(Exception e){}
    }
}
class B
{
    int u;
    int v;
}
class C
{
    int w;
    int x2;
}
class D
{
    int d1;
}
/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L1 mhp? L4 */
/* L1 mhp? L5 */
/* L1 mhp? L6 */
/* L1 mhp? L7 */
/* L1 mhp? L8 */
/* L1 mhp? L9 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L2 mhp? L5 */
/* L2 mhp? L6 */
/* L2 mhp? L7 */
/* L2 mhp? L8 */
/* L2 mhp? L9 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L3 mhp? L5 */
/* L3 mhp? L6 */
/* L3 mhp? L7 */
/* L3 mhp? L8 */
/* L3 mhp? L9 */
/* L4 mhp? L4 */
/* L4 mhp? L5 */
/* L4 mhp? L6 */
/* L4 mhp? L7 */
/* L4 mhp? L8 */
/* L4 mhp? L9 */
/* L5 mhp? L5 */
/* L5 mhp? L6 */
/* L5 mhp? L7 */
/* L5 mhp? L8 */
/* L5 mhp? L9 */
/* L6 mhp? L6 */
/* L6 mhp? L7 */
/* L6 mhp? L8 */
/* L6 mhp? L9 */
/* L7 mhp? L7 */
/* L7 mhp? L8 */
/* L7 mhp? L9 */
/* L8 mhp? L8 */
/* L8 mhp? L9 */
/* L9 mhp? L9 */
