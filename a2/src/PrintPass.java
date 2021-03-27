import visitor.GJNoArguDepthFirst;
import syntaxtree.*;
import tools.*;

public class PrintPass extends GJNoArguDepthFirst<String> {
    
    CGBuilder cg;
    String cname;
    String fname;

    public PrintPass(CGBuilder cg) {
        this.cg = cg;
        this.cname="";
        this.fname="";
    }
    
    public String visit(NodeToken n) { return n.tokenImage; }
    
    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( QStatement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
    public String visit(MainClass n) {
        String _ret=null;
        String cname = "";
        String fname = "";
        n.f0.accept(this);
        cname = n.f1.accept(this);
        this.cname = cname;
        cg.setCurrClass(cname);

        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        fname = n.f6.accept(this);
        this.fname = fname;
        cg.setCurrFn(fname);

        n.f7.accept(this);
        n.f8.accept(this);
        n.f9.accept(this);
        n.f10.accept(this);
        n.f11.accept(this);
        n.f12.accept(this);
        n.f13.accept(this);
        n.f14.accept(this);
        n.f15.accept(this);
        n.f16.accept(this);
        n.f17.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public String visit(ClassDeclaration n) {
        String _ret=null;
        String cname = "";
        n.f0.accept(this);
        cname = n.f1.accept(this);
        this.cname = cname;
        cg.setCurrClass(cname);

        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
    public String visit(ClassExtendsDeclaration n) {
        String _ret=null;
        String cname = "";
        n.f0.accept(this);
        cname = n.f1.accept(this);
        this.cname = cname;
        cg.setCurrClass(cname);

        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( QStatement() )*
    * f9 -> "return"
    * f10 -> Identifier()
    * f11 -> ";"
    * f12 -> "}"
    */
    public String visit(MethodDeclaration n) {
        String _ret=null;
        String fname = "";
        n.f0.accept(this);
        n.f1.accept(this);
        fname = n.f2.accept(this);
        cg.setCurrFn(fname);
        this.fname = fname;

        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        n.f8.accept(this);
        n.f9.accept(this);
        n.f10.accept(this);
        n.f11.accept(this);
        n.f12.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> <SCOMMENT1>
    * f1 -> Identifier()
    * f2 -> "alias?"
    * f3 -> Identifier()
    * f4 -> <SCOMMENT2>
    */
    public String visit(Query n) {
        String _ret=null;
        String id1 = "";
        String id2 = "";

                n.f0.accept(this);
        id1 =   n.f1.accept(this);
                n.f2.accept(this);
        id2 =   n.f3.accept(this);
                n.f4.accept(this);
        // System.out.println(id1 + "??" + id2);
        if(cg.isAlias(id1, id2)){System.out.println("Yes");}
        else{System.out.println("No");}

        return _ret;
    }
    
    /**
    * f0 -> <IDENTIFIER>
    */
    public String visit(Identifier n) {
        String _ret=null;
        _ret = n.f0.accept(this);
        return _ret;
    }
}
