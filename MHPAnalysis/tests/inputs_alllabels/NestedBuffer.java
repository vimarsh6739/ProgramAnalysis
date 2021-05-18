class NestedBuffer {
    public static void main(String[] args)
    {
        try{
            A t1;
            A t2;
            int ten;
            int twenty;
            B b;
            B b1;
            C c;
            b = new B();
            b1 = new B();
            t1 = new A();
            t2 = new A();
            c = new C();
            ten = 10;
            twenty = 20;
            c.a = ten;
            c.b = twenty;
            t1.b = b;
            t2.b = b;
            t1.b1 = b;
            t2.b1 = b;
            b1.c = c;
            b.c = c;
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }
        catch(InterruptedException e){}
    }   
}
class A extends Thread
{
    B b;
    B b1;
    public void run()
    {
        try{
            C c;
            int u;
            int v;
            boolean z;
            /*L1:*/synchronized(b)
            {
                c = b.c;
                z = true;
                b.z = z;
            }
            /*L2:*/synchronized(c)
            {
                u = c.a;
                v = c.b;
                u = u+v;
                c.a = u;
            }
            /*L3:*/synchronized(b1)
            {
                u = b1.x;
                v = b1.y;
                u = u+v;
                b1.x = u;
            }
        }
        catch(Exception e)
        {

        }
    }
}
class B
{
    int x;
    int y;
    boolean z;
    C c;
}
class C
{
    int a;
    int b;
}
/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L3 mhp? L3 */
