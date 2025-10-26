package main.visitor;

import main.ast.nodes.*;
import main.ast.nodes.DirectDeclarator.ArrayDeclarator;
import main.ast.nodes.DirectDeclarator.FunctionDeclarator;
import main.ast.nodes.DirectDeclarator.IdentifierDeclarator;
import main.ast.nodes.DirectDeclarator.NestedDeclarator;
import main.ast.nodes.Expr.*;
import main.ast.nodes.ExternalDeclaration.ExternalDeclaration;
import main.ast.nodes.ExternalDeclaration.FuncDefExtDec;
import main.ast.nodes.Stmt.*;

import java.util.*;

public class FunctionCallChanger extends Visitor{
    HashMap<String, List<Integer>> changedFunctions = new HashMap<>();
    private Boolean change = Boolean.FALSE;


    public Boolean getChange() {
        return change;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }

    public void setChangedFunctions(HashMap<String, List<Integer>> changedFunctions) {
        this.changedFunctions = changedFunctions;
    }

    @Override
    public Void visit(Program program) {
        program.getTranslationUnit().accept(this);
        return null;
    }

    @Override
    public Void visit(TranslationUnit translationUnit){
        for(ExternalDeclaration ext : translationUnit.getExternalDeclarations()){
            ext.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(FuncDefExtDec funcDefExtDec) {
        //update the number of arguments of the function
        funcDefExtDec.setNumOfArgs(funcDefExtDec.getNewNumOFArgs());
        //the body of the function
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
        return null;
    }

    @Override
    public Void visit(UnaryExpr unaryExpr) {
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
        if(expressionStatement.getExprStmt() != null)
            expressionStatement.getExprStmt().accept(this);
        return null;
    }

    @Override
    public Void visit(SelectionStatement selectionStatement) {
        return null;
    }

    @Override
    public Void visit(IterationStatement iterationStatement) {
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
        return null;
    }

    @Override
    public Void visit(InitDeclarator initDeclarator) {
        return null;
    }

    @Override
    public Void visit(Initializer initializer) {
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

    //auxiliary functions
    private List<Expr> flattenCommaExpr(CommaExpr commaExpr) {
        List<Expr> result = new ArrayList<>();
        for (Expr expr : commaExpr.getExpressions()) {
            if (expr instanceof CommaExpr) {
                result.addAll(flattenCommaExpr((CommaExpr) expr));
            } else {
                result.add(expr);
            }
        }
        return result;
    }

    private CommaExpr buildCommaExprFromList(List<Expr> exprs) {
        if (exprs == null || exprs.isEmpty()) {
            return null;
        }

        CommaExpr commaExpr = new CommaExpr(exprs.get(0));

        for (int i = 1; i < exprs.size(); i++) {
            commaExpr.addExpr(exprs.get(i));
        }

        return commaExpr;
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

        //calculating the number of arguments before change to find the signiture of the function
        int numOfArgs = 0;

        if (functionCallExpr.getArgumentExpressionList() != null) {
            // Special case: a single constant argument
            if ((functionCallExpr.getArgumentExpressionList().getExpressions().size() == 1) &&
                    (functionCallExpr.getArgumentExpressionList().getExpressions().get(0) instanceof Constant)) {
                numOfArgs = 1;
            } else {
                numOfArgs = numOfArgsCalculator(functionCallExpr.getArgumentExpressionList());
            }
        }
        //find the signiture of the function
        String funcSign="";
        if(functionCallExpr.getExpr() != null){
            if(functionCallExpr.getExpr() instanceof Identifier) {
                funcSign = ((Identifier) functionCallExpr.getExpr()).getName() + "-" + numOfArgs;
            }
        }

        //toRemove indexes
        List<Integer> indicesToRemove = changedFunctions.get(funcSign);


        if ((functionCallExpr.getArgumentExpressionList() != null) && (indicesToRemove != null)) {
            if(indicesToRemove.size() != 0)
                setChange(Boolean.TRUE);
            // make commaExpr tree flatten
            List<Expr> flatArgs = flattenCommaExpr(functionCallExpr.getArgumentExpressionList());

            // filter arguments that should not remove
            List<Expr> filteredArgs = new ArrayList<>();
            for (int i = 0; i < flatArgs.size(); i++) {
                if (!indicesToRemove.contains(i)) {
                    filteredArgs.add(flatArgs.get(i));
                }
            }

            //making new commaExpr using the previous filered list
            CommaExpr newCommaExpr = buildCommaExprFromList(filteredArgs);
            functionCallExpr.setArgumentExpressionList(newCommaExpr);
        }

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
