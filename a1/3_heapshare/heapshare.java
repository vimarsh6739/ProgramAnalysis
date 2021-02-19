import java.util.ArrayList;
import java.util.List;

public class heapshare {

    class InnerClass implements Runnable{
        
        List<String> s;

        InnerClass(){
            s = new ArrayList<String>();
        }

        // called by worker threads
        synchronized void append(String tid){
            s.add(tid);
            System.out.println("Added ["+tid+"]");
            System.out.println("Contents are :: "+s.toString());
        }

        // called by Main thread
        void show(){
            System.out.println(s);
        }

        @Override
        public void run(){
            this.append(Thread.currentThread().getName());
        }
    }
    public static void main(String[] args) {
        
        heapshare obj = new heapshare();
        heapshare.InnerClass base = obj.new InnerClass();
        
        System.out.print("Heap contents before calls :: ");
        base.show();

        // Declare threads and call inner class
        Thread t1 = new Thread(base, "Thread 1");
        Thread t2 = new Thread(base, "Thread 2");
        Thread t3 = new Thread(base, "Thread 3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {e.printStackTrace();}

        System.out.print("Heap contents after calls ::");
        base.show();
    }
}