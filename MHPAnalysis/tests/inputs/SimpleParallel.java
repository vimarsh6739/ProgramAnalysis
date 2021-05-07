class SimpleParallel
{
    public static void main(String[] args)
    {
        try{
        A t1;
        B t2;
        t1 = new A();
        t2 = new B();
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
    public void run()
    {
        try
        {
            int x;
            int y;
            x = 12;
            /*L1:*/System.out.println(x);
        }
        catch(Exception e){

        }
    }
}
class B extends Thread
{
    public void run(){
    try{
        int y;
        y = 20;
        /*L2:*/ System.out.println(y);
    }
    catch(Exception e){

    }
}
}
/*L1 mhp? L2*/