import syntaxtree.*;

public class Main {
    public static void main(String[] args) {
        try {
            QTACoJavaParser __ = new QTACoJavaParser(System.in);
            Node root = QTACoJavaParser.Goal();
            FieldPass p1 = new FieldPass();
            root.accept(p1);
            p1.cg.buildInheritanceInfo();
            p1.cg.printClassHierarchy();
            
            CGPass p2 = new CGPass(p1.cg);
            root.accept(p2);
            System.out.println("Program Parsed Successfully");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
