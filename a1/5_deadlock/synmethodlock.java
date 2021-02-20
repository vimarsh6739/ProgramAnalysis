class DiningPhilosopher implements Runnable{
    
    public int N;                      // the number of people
    public DiningPhilosopher right;    // the philosopher to the right
    final String[] choices = {"left","right"};

    DiningPhilosopher(int N){
        this.N = N;
        right=null;
    }

    void set_right(DiningPhilosopher rt){
        this.right=rt;
    }

    synchronized void pick(String tid, int ch){
        System.out.println("[" + tid + "]: picked "+choices[ch]+" fork");
        right.pick(tid,1-ch);
    }
        
    @Override
    public void run(){
        String tid = Thread.currentThread().getName();
        // Force deadlock
        while(true){
            pick(tid,0);
        }
    }
}

public class synmethodlock {
    public static void main(String[] args) {
        DiningPhilosopher p1 = new DiningPhilosopher(5);
        DiningPhilosopher p2 = new DiningPhilosopher(5);
        DiningPhilosopher p3 = new DiningPhilosopher(5);
        DiningPhilosopher p4 = new DiningPhilosopher(5);
        DiningPhilosopher p5 = new DiningPhilosopher(5);
        
        p1.set_right(p2);
        p2.set_right(p3);
        p3.set_right(p4);
        p4.set_right(p5);
        p5.set_right(p1);

        // Setup threads and start

        Thread t1 = new Thread(p1,"Thread 1");
        Thread t2 = new Thread(p2,"Thread 2");
        Thread t3 = new Thread(p3,"Thread 3");
        Thread t4 = new Thread(p4,"Thread 4");
        Thread t5 = new Thread(p5,"Thread 5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}