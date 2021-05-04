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
            // st.printVariables();
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
