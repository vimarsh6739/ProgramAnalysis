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

    public String visit(NodeToken n) { return n.tokenImage; }
    
    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> ( Query() )*
    * f3 -> <EOF>
    */
    public String visit(Goal n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
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
        n.f19.accept(this);
        n.f20.accept(this);
        n.f21.accept(this);
        n.f22.accept(this);
        n.f23.accept(this);
        n.f24.accept(this);
        n.f25.accept(this);
        n.f26.accept(this);
        n.f27.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public String visit(TypeDeclaration n) {
        String _ret=null;
        n.f0.accept(this);
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
    * f0 -> ( Ann() )*
    * f1 -> Statement()
    */
    public String visit(QParStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> <SCOMMENT1>
    * f1 -> Label()
    * f2 -> <SCOMMENT2>
    */
    public String visit(Ann n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> ":"
    */
    public String visit(Label n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | FieldAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | MessageSend()
    *       | PrintStatement()
    *       | SynchStatement()
    */
    public String visit(Statement n) {
        String _ret=null;
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "{"
    * f1 -> ( QParStatement() )*
    * f2 -> "}"
    */
    public String visit(Block n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public String visit(AssignmentStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "="
    * f4 -> Identifier()
    * f5 -> ";"
    */
    public String visit(FieldAssignmentStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
    public String visit(IfStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public String visit(WhileStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "synchronized"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public String visit(SynchStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> ";"
    */
    public String visit(PrintStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | FieldRead()
    *       | PrimaryExpression()
    */
    public String visit(Expression n) {
        String _ret=null;
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "&&"
    * f2 -> Identifier()
    */
    public String visit(AndExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "<"
    * f2 -> Identifier()
    */
    public String visit(CompareExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "+"
    * f2 -> Identifier()
    */
    public String visit(PlusExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "-"
    * f2 -> Identifier()
    */
    public String visit(MinusExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "*"
    * f2 -> Identifier()
    */
    public String visit(TimesExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    */
    public String visit(FieldRead n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> callStartMethod()
    *       | callNotifyMethod()
    *       | callNotifyAllMethod()
    *       | callWaitMethod()
    *       | callJoinMethod()
    */
    public String visit(MessageSend n) {
        String _ret=null;
        n.f0.accept(this);
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
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "notify"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
    public String visit(callNotifyMethod n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "notifyAll"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
    public String visit(callNotifyAllMethod n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "wait"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
    public String visit(callWaitMethod n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "join"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
    public String visit(callJoinMethod n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    */
    public String visit(PrimaryExpression n) {
        String _ret=null;
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public String visit(IntegerLiteral n) {
        String _ret=null;
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "true"
    */
    public String visit(TrueLiteral n) {
        String _ret=null;
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "false"
    */
    public String visit(FalseLiteral n) {
        String _ret=null;
        n.f0.accept(this);
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
    * f0 -> "this"
    */
    public String visit(ThisExpression n) {
        String _ret=null;
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public String visit(AllocationExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "!"
    * f1 -> Identifier()
    */
    public String visit(NotExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> <SCOMMENT1>
    * f1 -> Identifier()
    * f2 -> "mhp?"
    * f3 -> Identifier()
    * f4 -> <SCOMMENT2>
    */
    public String visit(Query n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }
    
}