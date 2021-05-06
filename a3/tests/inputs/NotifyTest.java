class NotifyTest {
    public static void main(String[] ag)
    {
        try{
            A a;
            B b;
            C c;
            a = new A();
            b = new B();
            c = new C();
            a.c = c;
            b.c = c;
            a.start();
            b.start();
            a.join();
            b.join();
        }
        catch(Exception e){}
    }    
}
class A extends Thread
{
    C c;
    public void run()
    {   
        try{
            int y;
            y = 20;
            /*L1:*/synchronized(c)
            {
                System.out.println(y);
                c.notifyAll();
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
            int y;
            a = this;
            y = 10;
            synchronized(c)
            {
                System.out.println(y);
                /*L2: */c.wait();
                System.out.println(y);
            }
        }
        catch(Exception e){}
    }
}
class C
{
    int z;
}
/*L1 mhp? L2*/