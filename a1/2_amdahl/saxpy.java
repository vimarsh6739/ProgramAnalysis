import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class kernel implements Runnable{
    
    int workers;
    int N; int G;
    float A; 
    int offset;
    long seed = 2021; 
    Random rng; 
    float [] X; float [] Y; float [] S;

    kernel(int N, int G, float A, int workers){
        this.workers = workers;
        this.N = N; 
        this.G = G;
        this.A = A;
        this.offset = N/workers;
        rng = new Random(this.seed);
        X = new float[N];
        Y = new float[N];
        S = new float[N];
    }

    // Serial code
    void initialize(){
        for(int i=0;i<N;++i){
            X[i] = rng.nextFloat();
        }
    }

    void print(int k){
        System.out.println("Number of elements = " + this.N);
        for (int i=0;i<k;++i){
            System.out.println("["+X[i] + "," +Y[i] + "," + S[i]+" ]");
        }
    }
    
    // Parallel code
    @Override
    public void run(){
        int tid = Integer.parseInt(Thread.currentThread().getName());

        //perform saxpy on current slice for G generations
        int start = tid * offset;
        int end = Math.min(N, start + offset);
        
        for(int g=0;g<G;++g){
            for(int i = start;i < end; ++i){
                S[i] = A * X[i] - Y[i];
                Y[i] = X[i];
                X[i] = S[i];
            }
        }
    }
}
public class saxpy{
    public static void main(String[] args) {

        int num_workers = 1;    
        float A = 0.9f;         // momentum
        int chk = 0;            // # of final values to print
        int N = 1<<20;          // 1M elements
        int G = 2000;           // generations

        // Parse args
        for(int i=0;i<args.length;++i){
            
            if(args[i].equals("--workers") || args[i].equals("-j")){
                num_workers = Integer.parseInt(args[++i]);
            }

            if(args[i].equals("--generations") || args[i].equals("-g")){
                G = Integer.parseInt(args[++i]);
            }

            if(args[i].equals("--check") || args[i].equals("-c")){
                chk = Integer.parseInt(args[++i]);
            }
        }
        
        kernel calc = new kernel(N, G, A, num_workers);
        calc.initialize();

        List<Thread> tList;
        tList = new ArrayList<Thread>();

        for(int i = 0; i<num_workers; ++i){
            String tid = String.valueOf(i);
            Thread t = new Thread(calc,tid);
            tList.add(t);
            t.start();
        } 

        try{
        for(int j=0;j<num_workers;++j){
            tList.get(j).join();
        }
        } catch(InterruptedException e){e.printStackTrace();}

        if(chk>0){
            calc.print(chk);
        }
    }
}