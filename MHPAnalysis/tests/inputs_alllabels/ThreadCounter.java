class ThreadCounter{
    public static void main(String[] args) {
        try{
            boolean T;
            Buffer b;
            int y;
            T1 ts;
            y=1;
            b = new Buffer();
            T=true;
            b.x=y;
            ts = new T1();
            ts.start();
/* L1: */   ts.join();
        } 
        catch (Exception e) {
        }
    }
}

class T1 extends Thread{
    boolean T;
    Buffer b;
    public void run(){
        try{
            T2 t2; 
            int tmp; 
            int o1;
            o1=1;
            while(T){
                t2 = new T2();
                t2.T = T;
                t2.b = b;
                t2.start();
/* L2: */       o1 = o1 * o1;
/* L3: */       synchronized(b){
/* L4: */           b.notifyAll();
                    tmp=b.x;
                    tmp = tmp + o1;
                    b.x = tmp;
                }
                t2.join();
            }
        }
        catch(Exception e){

        }
    }
}

class T2 extends Thread{
    boolean T;
    Buffer b;
    public void run(){
        try{
            int tmp; int o1; 
            o1=1;
            if(T){
/* L5: */       synchronized(b){
                    tmp = b.x;
                    tmp = tmp - o1;
                    b.x = tmp;
                }
            }
            else{
                synchronized(b){
/* L6: */           b.wait();
                }
            }
        }
        catch(Exception e){

        }
    }
}

class Buffer{
    int x;
}

/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L1 mhp? L4 */
/* L1 mhp? L5 */
/* L1 mhp? L6 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L2 mhp? L5 */
/* L2 mhp? L6 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L3 mhp? L5 */
/* L3 mhp? L6 */
/* L4 mhp? L4 */
/* L4 mhp? L5 */
/* L4 mhp? L6 */
/* L5 mhp? L5 */
/* L5 mhp? L6 */
/* L6 mhp? L6 */
