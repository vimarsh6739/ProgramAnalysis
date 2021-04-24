import syntaxtree.*;
import visitor.DepthFirstVisitor;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            
            new QParJavaParser(System.in);
            Node root = QParJavaParser.Goal();
            root.accept(new DepthFirstVisitor());
            System.out.println("Program parsed successfully");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
