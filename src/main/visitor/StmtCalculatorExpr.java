package main.visitor;

import main.ast.nodes.*;
import main.ast.nodes.DirectDeclarator.ArrayDeclarator;
import main.ast.nodes.DirectDeclarator.FunctionDeclarator;
import main.ast.nodes.DirectDeclarator.IdentifierDeclarator;
import main.ast.nodes.DirectDeclarator.NestedDeclarator;
import main.ast.nodes.Expr.*;
import main.ast.nodes.ExternalDeclaration.*;
import main.ast.nodes.Stmt.*;

/*GOALs:
 *   1. print out number of statements in each scope
 * */

public class StmtCalculatorExpr extends Visitor<Void>{
    String tempFuncNames;
    int tempDecLine;

    @Override
    public Void visit(Program program) {
        program.getTranslationUnit().accept(this);
        return null;
    }

    @Override
    public Void visit(TranslationUnit translationUnit){
        for(ExternalDeclaration ext : translationUnit.getExternalDeclarations()){
            if(ext instanceof FuncDefExtDec) {
                ext.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visit(FuncDefExtDec funcDefExtDec) {
        int numStmt = funcDefExtDec.getCompoundStatement().getNumStmt();
        funcDefExtDec.getDeclarator().getDirectDeclarator().accept(this);
        System.out.println("Line "+ funcDefExtDec.getLine() +": Stmt function " + tempFuncNames + " = " + numStmt);
        funcDefExtDec.getCompoundStatement().accept(this);
        return null;
    }

    @Override
    public Void visit(TypeSpecifier typeSpecifier) {
        return null;
    }

    @Override
    public Void visit(DeclarationSpecifiers declarationSpecifiers) {
        return null;
    }

    @Override
    public Void visit(DecExtDec declaration){
        tempDecLine = declaration.getDeclarationSpecifiers().getLine();
        if(declaration.getInitDeclaratorList() != null)
        {
            declaration.getInitDeclaratorList().accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Declarator declarator) {
        declarator.getDirectDeclarator().accept(this);
        return null;
    }

    @Override
    public Void visit(DeclarationList declarationList) {
        return null;
    }

    @Override
    public Void visit(BlockItem blockItem){
//        if(blockItem.getStatement() != null){
//            System.out.println("block item");
//            blockItem.getStatement().accept(this);
//        }
        return null;
    }

    @Override
    public Void visit(CompoundStatement compoundStatement) {
        for(BlockItem bl : compoundStatement.getBlocks())
        {
            if(bl.getDeclaration() != null)
            {
                bl.getDeclaration().accept(this);
            }
            if(bl.getStatement() != null) {
                bl.getStatement().accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visit(Identifier identifier) {

        return null;
    }

    @Override
    public Void visit(Constant constant) {
//        if(constant.getConstantValue() != null)
//            System.out.println("Line "+ constant.getLine() +"Expr " + constant.getConstantValue());
        return null;
    }

    @Override
    public Void visit(BinaryExpr binaryExpr) {
//        if(binaryExpr.getOperator() != null)
//            System.out.println("Line "+ binaryExpr.getLine() +"Expr " + binaryExpr.getOperator().toString());
        return null;
    }

    @Override
    public Void visit(UnaryExpr unaryExpr) {
//        if(unaryExpr.getOperator() != null)
//            System.out.println("Line "+ unaryExpr.getLine() +"Expr " + unaryExpr.getOperator().toString());
        return null;
    }

    @Override
    public Void visit(TernaryExpr ternaryExpr) {
        return null;
    }

    @Override
    public Void visit(CommaExpr commaExpr) {
        return null;
    }

    @Override
    public Void visit(AssignmentExpr assignmentExpr) {
        return null;
    }

    @Override
    public Void visit(DeclarationSpecifier declarationSpecifier) {
        return null;
    }

    @Override
    public Void visit(Pointer pointer) {
        return null;
    }

    @Override
    public Void visit(NestedDeclarator nestedDeclarator) {
        return null;
    }

    @Override
    public String visit(IdentifierDeclarator identifierDeclarator) {
        tempFuncNames = identifierDeclarator.getName();
        return null;
    }

    @Override
    public Void visit(ArrayDeclarator arrayDeclarator) {
        return null;
    }

    @Override
    public Void visit(FunctionDeclarator functionDeclarator) {
        functionDeclarator.getBaseDirectDeclarator().accept(this);
        return null;
    }

    @Override
    public Void visit(ParameterList parameterList) {
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {
        return null;
    }

    @Override
    public Void visit(IdentifierList identifierList) {
        return null;
    }

    @Override
    public Void visit(AbstractDeclarator abstractDeclarator) {
        return null;
    }

    @Override
    public Void visit(DirectAbstractDeclarator directAbstractDeclarator) {
        return null;
    }

    @Override
    public Void visit(StringLiteral stringLiteral) {
        return null;
    }

    @Override
    public Void visit(SpecifierQualifierList specifierQualifierList) {
        return null;
    }

    @Override
    public Void visit(IterationStatement iterationStatement) {
        if(iterationStatement.getCondition() != null)
            printExpr(iterationStatement.getCondition(), 0);
        if(iterationStatement.getStatement() instanceof CompoundStatement com) {
            if(com != null)
            {
                int numStmt = com.getNumStmt();
                System.out.println("Line "+ iterationStatement.getLine() +": Stmt "+ iterationStatement.getType() + " = " + numStmt);
                com.accept(this);
            }
        }
        return null;
    }

    public void printExpr(Expr expr, int state)
    {
        if(expr instanceof FunctionCallExpr){
            System.out.println("Line " + expr.getLine() + ": Expr " + ((Identifier)(((FunctionCallExpr) expr).getExpr())).getName());
        }
        if(expr instanceof AssignmentExpr){
            if(state == 8)
                System.out.println("Line " + tempDecLine + ": Expr " + ((AssignmentExpr) expr).getOperator());
            else
                System.out.println("Line " + expr.getLine() + ": Expr " + ((AssignmentExpr) expr).getOperator());
        }
        else if(expr instanceof CommaExpr) {
            if(state == 8)
                System.out.println("Line " + tempDecLine + ": Expr ,");
            else
                System.out.println("Line " + expr.getLine() + ": Expr ,");
        }
        else if(expr instanceof BinaryExpr) {
            if(state == 8)
                System.out.println("Line " + tempDecLine + ": Expr " + ((BinaryExpr) expr).getOperator());
            else
                System.out.println("Line " + expr.getLine() + ": Expr " + ((BinaryExpr) expr).getOperator());
        }
        else if(expr instanceof UnaryExpr) {
            if(state == 8)
                System.out.println("Line " + tempDecLine + ": Expr " + ((UnaryExpr) expr).getOperator());
            else
                System.out.println("Line " + expr.getLine() + ": Expr " + ((UnaryExpr) expr).getOperator());
        }
        if(expr instanceof Constant) {
            if(state == 8)
                System.out.println("Line " + tempDecLine + ": Expr " + ((Constant) expr).getConstantValue());
            else
                System.out.println("Line " + expr.getLine() + ": Expr " + ((Constant) expr).getConstantValue());
        }
        if(expr instanceof Identifier){
            if(state == 8)
                System.out.println("Line " + tempDecLine + ": Expr " + ((Identifier) expr).getName());
            else
                System.out.println("Line " + expr.getLine() + ": Expr " + ((Identifier) expr).getName());
        }
    }
    @Override
    public Void visit(ExpressionStatement expressionStatement) {
        if(expressionStatement != null)
            printExpr(expressionStatement.getExprStmt(), 0);
        return null;
    }

    @Override
    public Void visit(SelectionStatement selectionStatement) {
        printExpr(selectionStatement.getCondition(), 0);
        if(selectionStatement.getThenStmt() instanceof CompoundStatement co1)
        {
            if(co1 != null)
            {
                int numStmt = co1.getNumStmt();
                System.out.println("Line "+ selectionStatement.getCondition().getLine() +": Stmt selection = " + numStmt);
                co1.accept(this);
            }
        }
        for (int i = 0; i < selectionStatement.getElseIfThenStmts().size(); i++) {
            Stmt stmt = selectionStatement.getElseIfThenStmts().get(i);
            printExpr(selectionStatement.getElseIfConditions().get(i), 0);
            if (stmt instanceof CompoundStatement co2) {
                if (co2 != null) {
                    int numStmt = co2.getNumStmt();
                    System.out.println("Line " + selectionStatement.getElseIfConditions().get(i).getLine() + ": Stmt selection = " + numStmt);
                    co2.accept(this);
                }
            }
        }
        if(selectionStatement.getElseStmt() instanceof CompoundStatement co3)
        {
            if(co3 != null)
            {
                int numStmt = co3.getNumStmt();
                System.out.println("Line "+ selectionStatement.getElseLine() +": Stmt selection = " + numStmt);
                co3.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visit(ForCondition forCondition) {
        return null;
    }

    @Override
    public Void visit(ForExpr forExpr) {
        return null;
    }

    @Override
    public Void visit(ForDeclaration forDeclaration) {
        return null;
    }

    @Override
    public Void visit(InitDeclaratorList initDeclaratorList) {
        for(InitDeclarator init : initDeclaratorList.getInitDeclarators())
        {
            if(init != null)
            {
                if(init.getInitializer() != null)
                    init.getInitializer().accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visit(InitDeclarator initDeclarator) {
        return null;
    }

    @Override
    public Void visit(Initializer initializer) {
        if(initializer.getExpr() != null)
        {
            printExpr(initializer.getExpr(), 8);
        }
        return null;
    }

    @Override
    public Void visit(DesignationInitializerTuple designationInitializerTuple) {
        return null;
    }

    @Override
    public Void visit(InitializerList initializerList) {
        return null;
    }

    @Override
    public Void visit(Designation designation) {
        return null;
    }

    @Override
    public Void visit(Designator designator) {
        return null;
    }

    @Override
    public Void visit(JumpStatement jumpStatement) {
        if(jumpStatement.getReturnExpr() != null) {
            printExpr(jumpStatement.getReturnExpr(), 0);
        }
        return null;
    }

    @Override
    public Void visit(TypeName typeName) {
        return null;
    }

    @Override
    public Void visit(CompoundLiteralExpr compoundLiteralExpr) {
        return null;
    }

    @Override
    public Void visit(ArrayIndexingExpr arrayIndexingExpr) {
        return null;
    }

    @Override
    public Void visit(FunctionCallExpr functionCallExpr) {
        if(functionCallExpr.getExpr() != null)
            printExpr(functionCallExpr.getExpr(), 0);
        return null;
    }

    @Override
    public Void visit(CastExpr castExpr) {
        return null;
    }

    @Override
    public Void visit(PrefixUnaryExpr prefixUnaryExpr) {
        return null;
    }

    @Override
    public Void visit(SizeOfExpr sizeOfExpr) {
        return null;
    }
}


