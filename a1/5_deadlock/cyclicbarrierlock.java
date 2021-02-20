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
            System.out.println("["+tid+"] cleaning up barrier");

            Worker w = new Worker();
            Thread t = new Thread(w,"Cleanup Thread");
            t.start();
            System.out.println("["+tid+"] waiting for ["+t.getName()+"] to finish");

            // Unreachable code at runtime - results in deadlock
            // There's a cyclic dependency between (t, this.barrier, tid)
            try{
                t.join();
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