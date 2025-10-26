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
import java.util.Iterator;
import java.util.List;

public class UnrechabaleFunctionsRemover extends Visitor{
    private List<String> unrFuncs = new ArrayList<>();
    String tempFuncName;
    private Boolean change = Boolean.FALSE;


    public Boolean getChange() {
        return change;
    }
    public void setChange(Boolean change) {
        this.change = change;
    }

    public void setUnreachableFunctions(List<String> unrFuncs) {
        this.unrFuncs = unrFuncs;
    }

    @Override
    public Void visit(Program program) {
        program.getTranslationUnit().accept(this);
        return null;
    }

    public String functionNameFetcher(FuncDefExtDec funcDec) {
        int numOfArgs=0;
        if(funcDec.getDeclarator().getDirectDeclarator() instanceof FunctionDeclarator) {
            funcDec.getDeclarator().getDirectDeclarator().accept(this);
            if(((FunctionDeclarator) funcDec.getDeclarator().getDirectDeclarator()).getParameterList() != null)
                numOfArgs = ((FunctionDeclarator) funcDec.getDeclarator().getDirectDeclarator()).getParameterList().getParameters().size();
        }
        String curName = tempFuncName+"-"+numOfArgs;
        return curName;
    }
    @Override
    public Void visit(TranslationUnit translationUnit) {
        List<ExternalDeclaration> decls = translationUnit.getExternalDeclarations();
        Iterator<ExternalDeclaration> iterator = decls.iterator();

        while (iterator.hasNext()) {
            ExternalDeclaration ext = iterator.next();

            if (ext instanceof FuncDefExtDec) {
                FuncDefExtDec funcDef = (FuncDefExtDec) ext;
                String funcName = functionNameFetcher(funcDef);

                if (unrFuncs.contains(funcName)) {
                    setChange(Boolean.TRUE);
                    iterator.remove();
                }
            }
        }

        return null;
    }

    @Override
    public Void visit(FuncDefExtDec funcDefExtDec) {
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
        return null;
    }

    @Override
    public Void visit(CompoundStatement compoundStatement) {
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
        tempFuncName = identifierDeclarator.getName();
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
        return null;
    }
    @Override
    public Void visit(ExpressionStatement expressionStatement) {
        return null;
    }

    @Override
    public Void visit(SelectionStatement selectionStatement) {
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
}

