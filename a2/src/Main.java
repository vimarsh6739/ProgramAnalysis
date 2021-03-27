import syntaxtree.*;
import tools.CGBuilder;

public class Main {
    public static void main(String[] args) {
        try {
            new QTACoJavaParser(System.in);
            Node root = QTACoJavaParser.Goal();
            
            FieldPass p1 = new FieldPass();
            root.accept(p1);
            CGBuilder cg = p1.cg;
            cg.buildInheritanceInfo();
            // cg.printClassHierarchy();

            CGPass p2 = new CGPass(cg);
            root.accept(p2);
            // cg.printStatements();
            cg.buildLattice(); 
            cg.buildPointsToSets();
            cg.printLattice();
            
            // PrintPass p3 = new PrintPass(cg);
            // root.accept(p3);

            // System.out.println("Program Parsed Successfully");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
