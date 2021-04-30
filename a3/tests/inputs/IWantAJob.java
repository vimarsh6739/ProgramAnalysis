class IWantAJob{
    public static void main(String[] args) {
        try{
            Job j1;
            Job j2;
            j1 = new Job();
            j2 = new Job();
            j1.start();
            j2.start();
            synchronized(j1){
/* L1: */       synchronized(j2){
                    j1.notifyAll();
/* L2: */           j2.notifyAll();
                }
            }
            j1.join();
            j2.join();
        } 
        catch (Exception e) {
        }
    }
}

class Job extends Thread{
    public void run(){
        try{
            Job j;
            SubJob jj;
            j = this;
            synchronized(j){
                j.wait();
/* L3: */       jj = new SubJob();
                jj.j = j;
                jj.start();
/* L4: */       jj.notifyAll();
            }
            jj.join();
        }
        catch(Exception e){
        }
    }
}

class SubJob extends Thread{
    Job j;
    public void run(){
        try{
            synchronized(j){
/* L5: */       j.wait();
            }
        }
        catch(Exception e){}
    }
}


/* L3 mhp? L1 */
/* L4 mhp? L5 */
/* L3 mhp? L3 */
/* L2 mhp? L5 */
