class SharedBuffer {
    public static void main(String[] args)
    {
        try{
            A t1;
            C t2;
            B b;
            int one;
            t1 = new A();
            t2 = new C();
            b = new B();
            one = 0;
            b.x = one;
            one = 1;
            b.y = one;
            t1.b = b;
            t2.b = b;
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
    public void run()
    {
        try{
            int x;
            int y;
            boolean tb;
            /*L1:*/y = 20;
            tb = true;
            /*L2:*/synchronized(b)
            {
                b.z = tb;
                x = b.x;
                y = b.y;
                x = x+y;
                b.x = x;
                x = 10;
            }
            /*L3:*/System.out.println(x);
        }
        catch(Exception e)
        {

        }
    }
}
class C extends Thread
{
    B b;
    public void run()
    {
        try{
        int x;
        int y;
        boolean z;
        boolean fb;
        /*L4:*/x = 10;
        fb = false;
        y = 20;
        /*L5:*/synchronized(b)
        {
            b.z = fb;
            x = b.x;
            y = b.y;
            x = x-y;
            b.y = x;
            z = y < x;
        }
        /*L6:*/System.out.println(y);
     }
     catch(Exception e){}

    }
}
class B
{
    int x;
    int y;
    boolean z;
}
/*L1 mhp? L4*/
/*L1 mhp? L1*/
/*L2 mhp? L5*/
/*L2 mhp? L6*/
/*L6 mhp? L3*/