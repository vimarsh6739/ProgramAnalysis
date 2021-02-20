import java.util.*;

class DiningPhilosophers implements Runnable{
    
    int N;                  // the number of people
    Object [] forks;     // dummy object for synchronized block
    
    DiningPhilosophers(int N){
        this.N = N;
        this.forks = new Object[this.N];
        for(int i=0;i<N;++i){
            forks[i] = new Object();
        }
    }

    // Method called by each unique thread with a unique threadId
    void pickCutlery(int tid, String name) throws InterruptedException {
            while(true){        
                // Pickup left fork
                synchronized(forks[tid]){
                    System.out.println("["+name + "]: picked left fork");
                    
                    // Pickup right fork
                    synchronized(forks[(tid+1)%N]){
                        System.out.println("["+name + "]: picked right fork");
                        
                        System.out.println("["+name + "]: eating now");
                        Thread.sleep(1000);
                        
                        System.out.println("["+name + "]: releasing right fork");
                    }
                    
                    System.out.println("["+name + "]: releasing left fork");
                }
            }
    }
        
    @Override
    public void run(){
        String name = Thread.currentThread().getName();
        int tid = (int)(name.charAt(name.length()-1));
        tid -=49;
        try{
            pickCutlery(tid, name);
        } catch(InterruptedException e){}
    }
}


public class synobjlock {
    public static void main(String[] args) {

        // Using classic case of 5 philosophers
        DiningPhilosophers dp = new DiningPhilosophers(5);

        // Initialize and start threads
        List<Thread> tl;tl = new ArrayList<Thread>();
        int [] num_i = {0};
        for(int i=0;i<5;++i){
            num_i[0]=i;
            String name = "Thread " + String.valueOf(i+1);
            Thread t = new Thread(dp,name);
            tl.add(t);
        }

        // Start all threads
        for(int i=4;i>=0;--i){
            tl.get(i).start();
        }
    }
}