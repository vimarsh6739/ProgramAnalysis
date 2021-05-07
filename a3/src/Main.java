import syntaxtree.*;
import tools.*;

public class Main {
    public static void main(String[] args) throws Exception {
        
        boolean printCFG=false;
        for(int i = 0; i <args.length;++i){
            if(args[i].equals("--codegen")){
                printCFG=true;
            }
        }

        try {
            new QParJavaParser(System.in);
            Node root = QParJavaParser.Goal();
            SymbolPass pass1 = new SymbolPass();
            SymbolTable st = pass1.st;
            root.accept(pass1);
            
            // Print variables
            st.updateThreads();
            
            CFGPass pass2 = new CFGPass(st);
            root.accept(pass2);
            
            st.analyze();

            if(printCFG){
                st.printProgram();
                st.printSyncVariables();
                st.printWorklist();
            } 
            st.outputQueries();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
