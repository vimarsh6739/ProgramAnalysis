class UselessReadWrite{
    public static void main(String[] args) {
        try{
            Buffer b;
            Writer w1;
            int count; 
            count=0; 
            b = new Buffer();      
            w1 = new Writer();
            w1.b = b;

/* L1: */   w1.start();
/* L2: */   count=count+one;
            w1.join();
            System.out.println(count);
        } 
        catch (Exception e) {
        }
    }
}

class Reader extends Thread{
    Buffer b;
    public void run(){
        try{
           synchronized(b){
/* L3: */       b.notify();
            }
        }
        catch(Exception e){
        }
    }
}

class Writer extends Thread{
    Buffer b;
    public void run(){
        try{
            int x;
            Reader r;
/* L4: */   x=55;
            r = new Reader();
            r.start();
            System.out.println(x);
            r.join();
/* L5: */   synchronized(b){
                b.wait();
            }
        }
        catch(Exception e){
        }
    }
}

class Buffer{
}

/* L1 mhp? L1 */
/* L1 mhp? L2 */
/* L1 mhp? L3 */
/* L1 mhp? L4 */
/* L1 mhp? L5 */
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L2 mhp? L5 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L3 mhp? L5 */
/* L4 mhp? L4 */
/* L4 mhp? L5 */
/* L5 mhp? L5 */
