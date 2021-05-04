import syntaxtree.*;
import visitor.GJNoArguDepthFirst;
import tools.*;

public class SymbolPass extends GJNoArguDepthFirst<String> {
    
    SymbolTable st;
    boolean inFunction;
    public SymbolPass() {
        this.st = new SymbolTable();
        this.inFunction = false;   
    }
    
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
    * f14 -> "try"
    * f15 -> "{"
    * f16 -> ( VarDeclaration() )*
    * f17 -> ( QParStatement() )*
    * f18 -> "}"
    * f19 -> "catch"
    * f20 -> "("
    * f21 -> Identifier()
    * f22 -> Identifier()
    * f23 -> ")"
    * f24 -> "{"
    * f25 -> "}"
    * f26 -> "}"
    * f27 -> "}"
    */
    public String visit(MainClass n) {
        String _ret=null;
        String cname = "";
        n.f0.accept(this);
        cname = n.f1.accept(this);
        this.st.addClass(cname,true);
        
        // Ghost Static field corresponding to main class
        this.st.addMemberField(cname, "main");
        this.st.addThread("main");

        n.f2.accept(this);
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
        n.f13.accept(this);
        this.inFunction = true;
        n.f14.accept(this);
        n.f15.accept(this);
        n.f16.accept(this);
        n.f17.accept(this);
        n.f18.accept(this);
        n.f19.accept(this);
        n.f20.accept(this);
        n.f21.accept(this);
        n.f22.accept(this);
        n.f23.accept(this);
        n.f24.accept(this);
        n.f25.accept(this);
        n.f26.accept(this);
        this.inFunction = false;
        n.f27.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> "}"
    */
    public String visit(ClassDeclaration n) {
        String _ret=null;
        String cname = null;
        n.f0.accept(this);
        cname = n.f1.accept(this);
        this.st.addClass(cname, false);
        
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> "Thread"
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
    public String visit(ClassExtendsDeclaration n) {
        String _ret=null;
        String cname=null;
        n.f0.accept(this);
        cname=n.f1.accept(this);
        this.st.addClass(cname, true);
        
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public String visit(VarDeclaration n) {
        String _ret=null;
        String type = n.f0.accept(this);
        String var  = n.f1.accept(this);
        n.f2.accept(this);
        
        if(this.inFunction){
            this.st.addLocalField(type,var);
        }
        else{
            this.st.addMemberField(type,var);
        }
        return _ret;
    }
    
    /**
    * f0 -> "public"
    * f1 -> "void"
    * f2 -> "run"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> "{"
    * f6 -> "try"
    * f7 -> "{"
    * f8 -> ( VarDeclaration() )*
    * f9 -> ( QParStatement() )*
    * f10 -> "}"
    * f11 -> "catch"
    * f12 -> "("
    * f13 -> Identifier()
    * f14 -> Identifier()
    * f15 -> ")"
    * f16 -> "{"
    * f17 -> "}"
    * f18 -> "}"
    */
    public String visit(MethodDeclaration n) {
        String _ret=null;
        this.inFunction = true;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
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
        n.f13.accept(this);
        n.f14.accept(this);
        n.f15.accept(this);
        n.f16.accept(this);
        n.f17.accept(this);
        n.f18.accept(this);
        this.inFunction = false;
        return _ret;
    }
    
    /**
    * f0 -> BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public String visit(Type n) {
        String _ret=null;
        _ret = n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "boolean"
    */
    public String visit(BooleanType n) {
        String _ret=null;
        _ret = n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "int"
    */
    public String visit(IntegerType n) {
        String _ret=null;
        _ret = n.f0.accept(this);
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

    /**
     * f0 -> Identifier()
     * f1 -> "."
     * f2 -> "start"
     * f3 -> "("
     * f4 -> ")"
     * f5 -> ";"
     */
    public String visit(callStartMethod n) {
        String _ret=null;
        String var = null;
        var = n.f0.accept(this);
        this.st.addThread(var);

        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
     }
}
