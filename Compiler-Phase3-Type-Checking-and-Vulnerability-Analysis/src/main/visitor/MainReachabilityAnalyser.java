package main.visitor;

import main.ast.nodes.*;
import main.ast.nodes.DirectDeclarator.ArrayDeclarator;
import main.ast.nodes.DirectDeclarator.FunctionDeclarator;
import main.ast.nodes.DirectDeclarator.IdentifierDeclarator;
import main.ast.nodes.DirectDeclarator.NestedDeclarator;
import main.ast.nodes.Expr.*;
import main.ast.nodes.ExternalDeclaration.DecExtDec;
import main.ast.nodes.ExternalDeclaration.ExternalDeclaration;
import main.ast.nodes.ExternalDeclaration.FuncDefExtDec;
import main.ast.nodes.Stmt.*;

import java.util.ArrayList;
import java.util.List;

/*GOALs:
 *   1. this is checking rechability of functions from the main
 * */

public class MainReachabilityAnalyser extends Visitor<Void>{
    private ArrayList<String> reachableFunctions = new ArrayList<>();
    String tempFuncSign;

    public void addReachableFunc(String newReachFunc) {
        if (!reachableFunctions.contains(newReachFunc)) {
            reachableFunctions.add(newReachFunc);
        }
    }

    public ArrayList<String> getReachableFunctions() {
        return reachableFunctions;
    }

    public void checkThisFunction(TranslationUnit translationUnit, String funcSign){
        tempFuncSign = funcSign;
        translationUnit.accept(this);
    }

    public void checkMain(FuncDefExtDec main, String funcSign)
    {
        //adding main to reachables
        String temp = funcSign;
        addReachableFunc(temp);

        main.accept(this);
    }

    String findFuncName(FuncDefExtDec func){
        String name = "";
        if(func.getDeclarator().getDirectDeclarator() != null){
            if(func.getDeclarator().getDirectDeclarator() instanceof FunctionDeclarator) {
                if(((FunctionDeclarator) func.getDeclarator().getDirectDeclarator()).getBaseDirectDeclarator() instanceof IdentifierDeclarator)
                    name = ((IdentifierDeclarator) ((FunctionDeclarator) func.getDeclarator().getDirectDeclarator()).getBaseDirectDeclarator()).getName();
            }
        }

        return name;
    }

    @Override
    public Void visit(TranslationUnit translationUnit) {
        for (ExternalDeclaration ext : translationUnit.getExternalDeclarations()) {
            if(ext instanceof FuncDefExtDec){
                String name = findFuncName((FuncDefExtDec) ext);
                int numOfArgs = ((FuncDefExtDec) ext).getNumOfArgs();
                if((name + "-" + numOfArgs).equals(tempFuncSign)) {
                    ext.accept(this);
                }
            }
        }

        return null;
    }

    @Override
    public Void visit(FuncDefExtDec funcDefExtDec) {
        if(funcDefExtDec.getCompoundStatement() != null)
            funcDefExtDec.getCompoundStatement().accept(this);
        return null;
    }

    @Override
    public Void visit(DeclarationSpecifiers declarationSpecifiers) {
        return null;
    }

    @Override
    public Void visit(Declarator declarator) {
        return null;
    }

    @Override
    public Void visit(DeclarationList declarationList) {
        return null;
    }

    @Override
    public Void visit(CompoundStatement compoundStatement) {
        for(BlockItem bl : compoundStatement.getBlocks()){
            if(bl.getStatement() != null)
                bl.getStatement().accept(this);
            if(bl.getDeclaration() != null)
                bl.getDeclaration().accept(this);
        }
        return null;
    }

    @Override
    public Void visit(DecExtDec declaration){
        if(declaration.getInitDeclaratorList() != null)
        {
            declaration.getInitDeclaratorList().accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Identifier identifier) {
        return null;
    }

    @Override
    public Void visit(Constant constant) {
        return null;
    }

    @Override
    public Void visit(BinaryExpr binaryExpr) {
        binaryExpr.getFirstOperand().accept(this);
        binaryExpr.getSecondOperand().accept(this);
        return null;
    }

    @Override
    public Void visit(UnaryExpr unaryExpr) {
        unaryExpr.getOperand().accept(this);
        return null;
    }

    @Override
    public Void visit(TernaryExpr ternaryExpr) {
        ternaryExpr.getCondition().accept(this);
        ternaryExpr.getThenExpr().accept(this);
        ternaryExpr.getElseExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(CommaExpr commaExpr) {
        for(Expr expr : commaExpr.getExpressions()){
            if(expr != null)
                expr.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(AssignmentExpr assignmentExpr) {
        assignmentExpr.getRightExpr().accept(this);
        assignmentExpr.getLeftExpr().accept(this);
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
        return "";
    }

    @Override
    public Void visit(ArrayDeclarator arrayDeclarator) {
        return null;
    }

    @Override
    public Void visit(FunctionDeclarator functionDeclarator) {
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
    public Void visit(BlockItem blockItem) {
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
    public Void visit(ExpressionStatement expressionStatement) {
        expressionStatement.getExprStmt().accept(this);
        return null;
    }

    @Override
    public Void visit(SelectionStatement selectionStatement) {
        if(selectionStatement.getCondition() != null)
            selectionStatement.getCondition().accept(this);
        if(selectionStatement.getThenStmt() != null)
            selectionStatement.getThenStmt().accept(this);

        for(Expr expr : selectionStatement.getElseIfConditions()){
            if(expr != null)
                expr.accept(this);
        }
        for(Stmt stmt : selectionStatement.getElseIfThenStmts()){
            if(stmt != null)
                stmt.accept(this);
        }

        if(selectionStatement.getElseStmt() != null)
            selectionStatement.getElseStmt().accept(this);
        return null;
    }

    @Override
    public Void visit(IterationStatement iterationStatement) {
        if(iterationStatement.getCondition() != null)
            iterationStatement.getCondition().accept(this);
        if(iterationStatement.getForCondition() != null)
            iterationStatement.getForCondition().accept(this);
        if(iterationStatement.getStatement() != null)
            iterationStatement.getStatement().accept(this);
        return null;
    }

    @Override
    public Void visit(ForCondition forCondition) {
        if(forCondition.getConditionExpression() != null)
            forCondition.getConditionExpression().accept(this);
        if(forCondition.getUpdateExpression() != null)
            forCondition.getUpdateExpression().accept(this);
        return null;
    }

    @Override
    public Void visit(ForExpr forExpr) {
        for(Expr expr : forExpr.getExpressions()){
            if(expr != null)
                expr.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ForDeclaration forDeclaration) {
        return null;
    }

    @Override
    public Void visit(InitDeclaratorList initDeclaratorList) {
        for(InitDeclarator init : initDeclaratorList.getInitDeclarators()){
            if(init != null)
                init.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(InitDeclarator initDeclarator) {
        if(initDeclarator.getDeclarator() != null)
            initDeclarator.getDeclarator().accept(this);
        if(initDeclarator.getInitializer() != null)
            initDeclarator.getInitializer().accept(this);
        return null;
    }

    @Override
    public Void visit(Initializer initializer) {
        if(initializer.getInitializerList() != null)
            initializer.getInitializerList().accept(this);
        if(initializer.getExpr() != null)
            initializer.getExpr().accept(this);

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
        if(jumpStatement.getReturnExpr() != null)
            jumpStatement.getReturnExpr().accept(this);
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
        if(arrayIndexingExpr.getArrayExpr() != null)
            arrayIndexingExpr.getArrayExpr().accept(this);
        if(arrayIndexingExpr.getIndexExpr() != null)
            arrayIndexingExpr.getIndexExpr().accept(this);
        return null;
    }

    public int numOfArgsCalculator(CommaExpr commaExpr) {
        int numOfArgs = 0;

        // We iterate through all expressions in the CommaExpr
        for (Expr expr : commaExpr.getExpressions()) {
            // If the expression is an instance of CommaExpr, it means there's a nested argument list
            if (expr instanceof CommaExpr) {
                numOfArgs += numOfArgsCalculator((CommaExpr) expr);  // Recursively count arguments in the nested CommaExpr
            } else {
                // If it's a single expression (e.g., Constant, StringLiteral, etc.), count it as one argument
                numOfArgs += 1;
            }
        }

        return numOfArgs;
    }

    @Override
    public Void visit(FunctionCallExpr functionCallExpr) {
        String funcName="";
        if((functionCallExpr.getExpr() != null) && (functionCallExpr.getExpr() instanceof Identifier))
            funcName = ((Identifier) functionCallExpr.getExpr()).getName();

        int numOfArgs = 0;
        if (functionCallExpr.getArgumentExpressionList() != null) {
            // Check if the argument expression list has a single constant (e.g., StringLiteral)
            if ((functionCallExpr.getArgumentExpressionList().getExpressions().size() == 1) &&
                    (functionCallExpr.getArgumentExpressionList().getExpressions().get(0) instanceof Constant)) {
                numOfArgs = 1;
            } else {
                // Otherwise, use numOfArgsCalculator to recursively count all arguments
                numOfArgs = numOfArgsCalculator(functionCallExpr.getArgumentExpressionList());
            }
        }
//        System.out.println("function call " + funcName + " with " + numOfArgs +" args");
        String tempReachedFunc = (String)funcName+"-"+numOfArgs;
        addReachableFunc(tempReachedFunc);
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

    @Override
    public Void visit(TypeSpecifier typeSpecifier) {
        return null;
    }

}


