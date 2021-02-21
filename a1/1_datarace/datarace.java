public class datarace implements Runnable {
    
    // Access to variables is not data race-free
    // In addition, the program will have race-conditions
    // during runtime
    int reward;
    int num_workers;
    
    int get_reward(){return this.reward;}
    int get_workers(){return this.num_workers;}
    
    void set_reward(int v){this.reward = v;}
    void set_workers(int v){this.num_workers = v;}

    void addPushups(int reps){
        int t1 = get_reward();
        set_reward(t1 + reps);
        int t2 = get_workers();
        set_workers(t2 + 1);
    }

    void cheatDay(){
        int t1 = get_reward();
        set_reward(t1/2);
        int t2 = get_workers();
        set_workers(t2-1);
    }

    @Override
    public void run(){
        try{Thread.sleep(0,5);}catch(InterruptedException e){}
        addPushups(50);
    }

    void startExecution(){
        Thread t = new Thread(this);
        try {
            t.start();
            Thread.sleep(0,10);
            this.cheatDay();
            System.out.println("Middle: [" + this.get_workers() + "," + this.get_reward()+"]");
            t.join();
        } catch (InterruptedException e) { e.printStackTrace();}
    }

    public static void main(String args[]){
        datarace o = new datarace();
        System.out.println("Initial: [" + o.get_workers() + "," + o.get_reward()+"]");
        o.startExecution();
        System.out.println("Final: [" + o.get_workers() + "," + o.get_reward()+"]");
    }
}