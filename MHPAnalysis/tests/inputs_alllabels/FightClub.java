
class FightClub{
    public static void main(String[] args) {
        try{
            Rival uncle1;
            Rival uncle2;
            Buffer b1;
            Buffer b2;
            int one;int two;
            one=1;
            two=2;
            b1 = new Buffer();
            b2 = new Buffer();
            uncle1 = new Rival();
            uncle2 = new Rival();
            uncle1.b1 = b1; uncle2.b1 = b1;
            uncle1.b2 = b2; uncle2.b2 = b2;
            uncle1.start();
            uncle2.start();

/* L1: */   synchronized(b1){
/* L2: */       synchronized(b2){
/* L3: */           b2.notifyAll();
                }
            }

            uncle1.join();
            uncle2.join();
        } 
        catch (Exception e) {
        }
    }
}

class Rival extends Thread{
    Buffer b1;
    Buffer b2;
    public void run(){
        try{
            synchronized(b1){
/* L4: */       synchronized(b2){
                    b2.wait();
                }
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
/* L2 mhp? L2 */
/* L2 mhp? L3 */
/* L2 mhp? L4 */
/* L3 mhp? L3 */
/* L3 mhp? L4 */
/* L4 mhp? L4 */

