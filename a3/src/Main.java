import syntaxtree.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            
            new QParJavaParser(System.in);
            Node root = QParJavaParser.Goal();
            SymbolPass pass1 = new SymbolPass();
            root.accept(pass1);
            // Print variables
            pass1.st.printVariables();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
