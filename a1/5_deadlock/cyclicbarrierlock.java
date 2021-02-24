import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class cyclicbarrierlock {

    CyclicBarrier barrier;
    int num_workers;

    class Worker implements Runnable{
        @Override
        public void run(){
            String tid = Thread.currentThread().getName();
            System.out.println("["+tid+"] waiting at barrier");
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class BarrierHandler implements Runnable{
        @Override
        public void run(){
            String tid = Thread.currentThread().getName();
            System.out.println("["+tid+"] executing common code");

            Worker w = new Worker();

            Thread t1 = new Thread(w,tid+"-child1");
            Thread t2 = new Thread(w,tid+"-child2");
            Thread t3 = new Thread(w,tid+"-child3");
            Thread t4 = new Thread(w,tid+"-child4");
            Thread t5 = new Thread(w,tid+"-child5");
            
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();            

            System.out.println("["+tid+"] waiting for ["+t1.getName()+"] to finish");
            System.out.println("["+tid+"] waiting for ["+t2.getName()+"] to finish");
            System.out.println("["+tid+"] waiting for ["+t3.getName()+"] to finish");
            System.out.println("["+tid+"] waiting for ["+t4.getName()+"] to finish");
            System.out.println("["+tid+"] waiting for ["+t5.getName()+"] to finish");

            // Unreachable code at runtime - results in deadlock
            // There's a cyclic dependency between (tid and it's child threads)
            // Child threads need tid to exit to execute common code,
            // and tid needs child threads to finish to exit common code.
            try{
                t1.join();
                t2.join();
                t3.join();
                t4.join();
                t5.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    cyclicbarrierlock(int num_workers){
        this.num_workers = num_workers;
        this.barrier = null;
    }

    // Create cyclic barrier, threads and call them
    void startExecution(){
        
        this.barrier = new CyclicBarrier(this.num_workers, new BarrierHandler());

        for(int i=1;i<=this.num_workers;++i){
            Thread t = new Thread(new Worker());
            t.setName("Thread" + i);
            t.start();
        }
    }
    public static void main(String[] args) {
        cyclicbarrierlock o = new cyclicbarrierlock(5);
        o.startExecution();
    }
}