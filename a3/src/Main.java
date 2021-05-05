import syntaxtree.*;
import tools.*;

public class Main {
    public static void main(String[] args) throws Exception {
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
            st.printProgram();
            st.printSyncVariables();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
