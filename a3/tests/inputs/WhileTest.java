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
                    /*L7:*/c.x2 = y1;
                    }
                }
                /*L6:*/x1 = y1 < ten;
            }
            /*L_join:*/a.join();

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
            /*L2:*/x = x + y;
            synchronized(b)
            {
                b.u = x;
                synchronized(c)
                {
                    c.w = x;
                    c.notifyAll();//no effect on anyone
                    synchronized(d)
                    {
                        /*L3:*/d.d1 = x;
                        d.notify();
                    }
                    /*L4:*/c.x2 = y;
                }
                b.v = x;
                /*L_wait:*/b.wait();
                /*L5:*/y = b.u;
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
/*L1 mhp? L2*/
/*L2 mhp? L7*/
/*L1 mhp? L5*/
/*L4 mhp? L7*/
/*L_wait mhp? L7*/
/*L_join mhp? L5*/