import java.util.ArrayList;
import java.util.List;

import syntaxtree.*;
import visitor.GJNoArguDepthFirst;
import tools.*;

/** 
 * We add all references, statememnts and call graph information in 
 * this pass.
 */
public class CGPass extends GJNoArguDepthFirst<String> {

    CGBuilder cg;
    Operations op;
    boolean fromFunction;
    String curr_cname;
    String curr_fname;

    String e_arg1;              // Expression arg 1
    String e_arg2;              // Expression arg 2
    List<String> e_args;        // Function arg if expression = messageSend

    public String visit(NodeToken n) { return n.tokenImage; }    

    public CGPass(CGBuilder cg) {
        this.cg = cg;
        this.op = Operations.NONE;
        this.fromFunction=false;
        this.curr_cname = "";
        this.curr_fname = "";
        this.e_arg1 = "";   
        this.e_arg2 = "";
        this.e_args = new ArrayList<>();
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
        this.curr_cname = cname;

        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        fname = n.f6.accept(this);
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
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
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
        this.curr_fname = fname;
        this.fromFunction = true;

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
        n.f0.accept(this);
        n.f1.accept(this);
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
        String x = ""; 
        x = n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);

        cg.addStatement(this.op, x, this.e_arg1, this.e_arg2, this.e_args);
        this.op = Operations.NONE;
        this.e_arg1 = "";
        this.e_arg2 = "";
        this.e_args.clear();
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
        String x;
        x = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg1 = n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        this.e_arg2 = n.f5.accept(this);
        n.f6.accept(this);
        this.op=Operations.ARRAYASSIGN;
        cg.addStatement(this.op, x, this.e_arg1, this.e_arg2, this.e_args);
        this.op = Operations.NONE;
        this.e_arg1 = "";
        this.e_arg2 = "";
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
        String x;
        x = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg1 = n.f2.accept(this);
        n.f3.accept(this);
        this.e_arg2=  n.f4.accept(this);
        n.f5.accept(this);
        this.op=Operations.STORE;
        cg.addStatement(this.op, x, this.e_arg1, this.e_arg2, this.e_args);       
        this.op=Operations.NONE;
        this.e_arg1="";
        this.e_arg2="";
        this.e_args.clear();
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
        _ret = n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "&&"
    * f2 -> Identifier()
    */
    public String visit(AndExpression n) {
        String _ret="AND";
        this.op = Operations.AND;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "<"
    * f2 -> Identifier()
    */
    public String visit(CompareExpression n) {
        String _ret="LEQ";
        this.op=Operations.LEQ;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "+"
    * f2 -> Identifier()
    */
    public String visit(PlusExpression n) {
        String _ret="ADD";
        this.op=Operations.ADD;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "-"
    * f2 -> Identifier()
    */
    public String visit(MinusExpression n) {
        String _ret="SUB";
        this.op=Operations.SUB;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "*"
    * f2 -> Identifier()
    */
    public String visit(TimesExpression n) {
        String _ret="MULT";
        this.op=Operations.MULT;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    */
    public String visit(ArrayLookup n) {
        String _ret="ARRAYLOOKUP";
        this.op=Operations.ARRAYLOOKUP;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "length"
    */
    public String visit(ArrayLength n) {
        String _ret="ARRAYLENGTH";
        this.op=Operations.ARRAYLENGTH;
        this.e_arg1 = n.f0.accept(this);
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
        String _ret="LOAD";
        this.op=Operations.LOAD;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
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
        String _ret="FCALL";
        this.op=Operations.FCALL;
        this.e_arg1 = n.f0.accept(this);
        n.f1.accept(this);
        this.e_arg2 = n.f2.accept(this);
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
        String fp = "";
        fp = n.f0.accept(this);
        this.e_args.add(fp);
        
        n.f1.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> ","
    * f1 -> Identifier()
    */
    public String visit(ArgRest n) {
        String _ret=null;
        String fp = "";
        n.f0.accept(this);
        fp = n.f1.accept(this);
        this.e_args.add(fp);
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
        _ret = n.f0.accept(this);
        switch(n.f0.which){
            case 0:
            case 1: 
            case 2:
                this.e_arg1 = _ret;
                _ret = "ASSIGNCONST";
                this.op=Operations.ASSIGNCONST;
                break;
            case 3:
            case 4:
                this.e_arg1 = _ret;
                _ret = "ASSIGN";
                this.op=Operations.ASSIGN;
                break;
            case 7:
                _ret = "NOT";
                this.op=Operations.NOT;
                break;
            default:
                break;
        }
        return _ret;
    }
    
    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public String visit(IntegerLiteral n) {
        String _ret=null;
        _ret = n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "true"
    */
    public String visit(TrueLiteral n) {
        String _ret=null;
        _ret = n.f0.accept(this);
        return _ret;
    }
    
    /**
    * f0 -> "false"
    */
    public String visit(FalseLiteral n) {
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
    * f0 -> "this"
    */
    public String visit(ThisExpression n) {
        String _ret=null;
        _ret = n.f0.accept(this);
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
        // Return class for allocation
        String _ret="ARRAYALLOCATE";
        this.op=Operations.ARRAYALLOCATE;
        this.e_arg1 = "int[]";
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
        // Return class for allocation
        String _ret="ALLOCATE";
        this.op=Operations.ALLOCATE;
        n.f0.accept(this);
        this.e_arg1 = n.f1.accept(this);
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
        _ret = n.f0.accept(this);
        this.e_arg1 = n.f1.accept(this);
        return _ret;
    }
}
