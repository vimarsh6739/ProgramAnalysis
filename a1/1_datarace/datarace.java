class PushupMeter {
    
    // Access to variables is not data race-free
    // In addition, the program will have race-conditions
    // during runtime
    int reward;
    int num_workers;
    
    int get_reward(){
        return this.reward;
    }
    
    void set_reward(int v){
        this.reward = v;
    }

    int get_workers(){
        return this.num_workers;
    }

    void set_workers(int v){
        this.num_workers = v;
    }

    void addPushups(int reps){
        int t1 = get_reward();
        int t2 = get_workers();

        set_reward(t1 + reps);
        set_workers(t2 + 1);
    }

    void cheatDay(){
        int t1 = get_reward();
        int t2 = get_workers();
        
        set_workers(t2-1);
        set_reward(t1/2);
    }
}

public class datarace {

    public static void main(String[] args)
        throws InterruptedException {
        
        PushupMeter o = new PushupMeter();

        Thread t1 = new Thread(()->{
            try{Thread.sleep(0,5);}
            catch(InterruptedException e){}
            o.addPushups(50);
        });
        Thread t2 = new Thread(()->{
            try{Thread.sleep(0,5);}
            catch(InterruptedException e){}
            o.cheatDay();
        });

        System.out.println(o.get_reward());       // Prints initial values 
        System.out.println(o.get_workers());
        System.out.println("---------------");
        
        t1.start(); t2.start();

        Thread.sleep(0,15);
        System.out.println(o.get_reward());       // This might print buggy values 
        System.out.println(o.get_workers());      // as t1, t2 and t3 are still running
        System.out.println("---------------");
        
        t1.join(); t2.join();
        
        System.out.println(o.get_reward());       // This should print the final values
        System.out.println(o.get_workers());  
    }
}