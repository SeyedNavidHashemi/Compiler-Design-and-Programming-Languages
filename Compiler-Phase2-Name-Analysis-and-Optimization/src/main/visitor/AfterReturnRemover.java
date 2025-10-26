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

import java.util.ArrayList;
import java.util.Objects;

public class AfterReturnRemover extends Visitor{
    private Boolean change = Boolean.FALSE;

    public Boolean getChange() {
        return change;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }

    @Override
    public Void visit(Program program) {
        program.getTranslationUnit().accept(this);
        return null;
    }

    @Override
    public Void visit(TranslationUnit translationUnit){
        for(ExternalDeclaration ext : translationUnit.getExternalDeclarations()){
            if((ext != null) && (ext instanceof FuncDefExtDec))
                ext.accept(this);
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
        ArrayList<BlockItem> blocks = compoundStatement.getBlocks();
        int returnIndex = -1;

        // Step 1: Find index of first return statement
        for (int i = 0; i < blocks.size(); i++) {
            BlockItem bl = blocks.get(i);
            if (bl.getStatement() instanceof JumpStatement) {
                if(Objects.equals(((JumpStatement) bl.getStatement()).getJumpType(), "return")) {
                    returnIndex = i;
                    break;
                }
            }
        }

        // Step 2: Remove all BlockItems after the return statement
        if (returnIndex != -1 && returnIndex < blocks.size() - 1) {
            setChange(Boolean.TRUE);
            ArrayList<BlockItem> trimmed = new ArrayList<>(blocks.subList(0, returnIndex + 1));
            compoundStatement.setBlocks(trimmed);
        }

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
        return null;
    }

    @Override
    public Void visit(SelectionStatement selectionStatement) {
        if(selectionStatement.getThenStmt() != null) {
            selectionStatement.getThenStmt().accept(this);
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
        if(iterationStatement.getStatement() != null)
            iterationStatement.getStatement().accept(this);
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

    @Override
    public Void visit(FunctionCallExpr functionCallExpr) {
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
