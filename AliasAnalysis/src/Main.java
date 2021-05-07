import syntaxtree.*;
import tools.CGBuilder;

public class Main {
    public static void main(String[] args) {

        boolean showProgress = false;
        boolean showFinal = false;
        boolean showCHA = false;
        
        // Parse args
        for(int i = 0;i<args.length;++i){
            if(args[i].equals("-p") || args[i].equals("-progress")){
                showProgress = true; showFinal = true;
            }
            else if(args[i].equals("-f") || args[i].equals("-final")){
                showFinal = true;
            }
            else if(args[i].equals("-c") || args[i].equals("-cha")){
                showCHA = true;
            }
            else if(args[i].equals("-g")){
                showProgress=true;
                showCHA=true;
                showFinal=true;
            }
        }

        try {
            new QTACoJavaParser(System.in);
            Node root = QTACoJavaParser.Goal();
            
            // First pass
            FieldPass p1 = new FieldPass();
            root.accept(p1);
            
            CGBuilder cg = p1.cg;
            cg.buildInheritanceInfo();

            if(showCHA){
                cg.printClassHierarchy();
            }

            // Second pass
            CGPass p2 = new CGPass(cg);
            root.accept(p2);
            
            cg.buildLattice(); 
            
            cg.buildPointsToSets(showProgress);
            
            if(showFinal && !showProgress){
                cg.printLattice();
            }
            
            // Final pass
            PrintPass p3 = new PrintPass(cg);
            root.accept(p3);
            
            // System.out.println("Program Parsed Successfully");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
