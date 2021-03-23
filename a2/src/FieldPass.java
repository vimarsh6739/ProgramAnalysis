import syntaxtree.*;
import visitor.GJNoArguDepthFirst;
import tools.*;

/** 
 * We add info about all classes, member variables, member functions 
 * and their local variables to the symbol table in this pass.
 */
public class FieldPass extends GJNoArguDepthFirst<String> {

    CGBuilder cg;
    boolean fromFunction;
    String curr_cname;
    String curr_fname;
    public String visit(NodeToken n) { return n.tokenImage; }    

    public FieldPass() {
        cg = new CGBuilder();
        this.fromFunction=false;
        this.curr_cname = "";
        this.curr_fname = "";
    }

    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public String visit(Goal n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
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
        cg.addClass(cname, null);
        this.curr_cname = cname;

        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        fname = n.f6.accept(this);
        cg.addMethod(fname);
        this.curr_fname = fname;

        n.f7.accept(this);
        n.f8.accept(this);
        n.f9.accept(this);
        n.f10.accept(this);
        n.f11.accept(this);
        n.f12.accept(this);
        n.f13.accept(this);
        
        this.fromFunction = true;
        n.f14.accept(this);
        n.f15.accept(this);
        n.f16.accept(this);
        this.fromFunction = false;
        n.f17.accept(this);
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
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public String visit(ClassDeclaration n) {
        String _ret=null;
        String cname = "";
        
        n.f0.accept(this);
        cname = n.f1.accept(this);
        cg.addClass(cname, null);
        this.curr_cname = cname;

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
        String pname = "";
        n.f0.accept(this);
        cname = n.f1.accept(this);
        this.curr_cname = cname;

        n.f2.accept(this);
        pname = n.f3.accept(this);
        cg.addClass(cname, pname);
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
        String type = "";
        String name = "";
        
        type = n.f0.accept(this);
        name = n.f1.accept(this);
        n.f2.accept(this);
        
        if(this.fromFunction)
            cg.addLocalField(type, name);
        else
            cg.addClassField(type, name);

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
        cg.addMethod(fname);
        this.curr_fname = fname;
        this.fromFunction = true;
        cg.addLocalField(this.curr_cname, "this");

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
        this.fromFunction = false;

        return _ret;
    }
    
    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    public String visit(FormalParameterList n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public String visit(FormalParameter n) {
        String _ret=null;
        String type = ""; String name = "";
        type = n.f0.accept(this);
        name = n.f1.accept(this);
        if(this.fromFunction)
            cg.addLocalField(type, name);
        return _ret;
    }
    
    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public String visit(FormalParameterRest n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public String visit(Type n) {
        String _ret=null;
        _ret = n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public String visit(ArrayType n) {
        String _ret="int[]";
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "boolean"
    */
    public String visit(BooleanType n) {
        String _ret="boolean";
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "int"
    */
    public String visit(IntegerType n) {
        String _ret="int";
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> ( Query() )*
    * f1 -> Statement()
    */
    public String visit(QStatement n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
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
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | FieldAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public String visit(Statement n) {
        String _ret=null;
        n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "{"
    * f1 -> ( QStatement() )*
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
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Identifier()
    * f6 -> ";"
    */
    public String visit(ArrayAssignmentStatement n) {
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
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
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
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    */
    public String visit(ArrayLookup n) {
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
    * f2 -> "length"
    */
    public String visit(ArrayLength n) {
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
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ArgList() )?
    * f5 -> ")"
    */
    public String visit(MessageSend n) {
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
    * f1 -> ( ArgRest() )*
    */
    public String visit(ArgList n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> ","
    * f1 -> Identifier()
    */
    public String visit(ArgRest n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
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
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Identifier()
    * f4 -> "]"
    */
    public String visit(ArrayAllocationExpression n) {
        String _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
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
}
