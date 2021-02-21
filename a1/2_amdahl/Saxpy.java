import java.util.Random;

public class Saxpy {
    int num_workers;        // # of threads launched
    int N;                  // 1M elements
    int G;                  // generations
    float A;                // momentum
    long seed = 2021;
    Random rng;
    float X[], Y[], S[];    // arrays in global memory
    
    // Serial code for initialization
    public Saxpy(int num_workers, int N, int G, float A){
        this.num_workers = num_workers;
        this.N = N;
        this.G = G;
        this.A = A;
        rng = new Random(this.seed);
        X = new float[N];
        Y = new float[N];
        S = new float[N];

        // initialize X, Y
        for(int i=0;i<N;++i){
            X[i] = rng.nextFloat();
            Y[i] = rng.nextFloat();
        }
    }

    // Parallel kernel called by thread(s)
    class Kernel implements Runnable{

        int start, stop;

        Kernel(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public void run(){
            // perform saxpy on the current slice
            for(int g=0;g<G;++g){
                for(int i = start;i < stop; ++i){
                    S[i] = A * X[i] - Y[i];
                    Y[i] = X[i];
                    X[i] = S[i];
                }
            }
        }
    }

    // create threads and execute
    void startExecution(){

        Kernel [] kList = new Kernel[this.num_workers];
        Thread [] tList = new Thread[this.num_workers];

        int offset = this.N / this.num_workers;

        for(int i = 0;i < this.num_workers;++i){
            int start = i * offset;
            int stop = Math.min(start+offset, this.N);
            kList[i] = new Kernel(start,stop);
            tList[i] = new Thread(kList[i]);
            tList[i].start();
        }

        try {
            for(int i=0;i<this.num_workers;++i){
                tList[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        int num_workers = 1;    // default # of threads
        int G = 2000;           // default # of generations
            
        // Parse args
        for(int i=0;i<args.length;++i){
            
            if(args[i].equals("--workers") || args[i].equals("-j")){
                num_workers = Integer.parseInt(args[++i]);
            }

            if(args[i].equals("--generations") || args[i].equals("-g")){
                G = Integer.parseInt(args[++i]);
            }
        }

        // Start execution
        Saxpy o = new Saxpy(num_workers, 1<<20, G, 0.9f);
        o.startExecution();
    }
}
