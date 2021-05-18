class ReadWrite{
    public static void main(String[] args) {
        try{
            Buffer b;
            Reader r1;
            Reader r2;
            Writer w1;
            int count; int om; 
            count=0; om=1;
            b = new Buffer();
            b.x = count;  
            b.one=om;  
            r1 = new Reader();
            r2 = new Reader();
            w1 = new Writer();
            r1.b = b;
            r2.b = b;
            w1.b = b;
            w1.start();
/* L1: */   r1.start();
            r2.start();
/* L2: */   count=count+one;
            w1.join();
            r1.join();
            r2.join();
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
            int y; int oy;
            oy=b.one;
/* L3: */   synchronized(b){
/* L4: */       y = b.x;
                y = y+oy;
                b.x = y;
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
            int z;int oz;
            oz=b.one;
/* L5: */   synchronized(b){
/* L6: */       z = b.x;
                z = z-oz;
                b.x = z;
            }
        }
        catch(Exception e){
        }
    }
}

class Buffer{
    int x;
    int one;
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
