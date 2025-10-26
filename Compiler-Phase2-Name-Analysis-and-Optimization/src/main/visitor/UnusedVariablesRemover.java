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

import java.util.*;

public class UnusedVariablesRemover extends Visitor<Void>{

    ArrayList<String> DeclaredFunctions = new ArrayList<>();
    HashMap<String, List<Integer>> changedFunctions = new HashMap<>();
    String tempFuncNames;
    int tempFuncLine;
    int tempDecLine;
    int tempForLine;
    TranslationUnit tempTranslation;
    ExternalDeclaration tempExtDec;
    FuncDefExtDec tempFunc;
    BlockItem tempBlockItem;
    CompoundStatement tempComp;
    ArrayList<String> unusedVariables;
    List<Integer> toRemoveIndices;
    private Boolean change = Boolean.FALSE;

    public ArrayList<String> getDeclaredFunctions() {
        return DeclaredFunctions;
    }

    public Boolean getChange() {
        return change;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }

    public HashMap<String, List<Integer>> getChangedFunctions() {
        return changedFunctions;
    }

    public void setUnusedVariables(ArrayList<String> unusedVariables) {
        this.unusedVariables = unusedVariables;
    }

    // Check the declarations to remove unused variables
    public void checkDeclaration(DecExtDec declaration) {
        if (declaration.getInitDeclaratorList() == null) {
            String varName = declaration.getDeclarationSpecifiers()
                    .getDeclarationSpecifiers()
                    .get(1)
                    .getName();
            if (unusedVariables.contains(varName + "&" + tempDecLine)) {
                setChange(Boolean.TRUE);
                tempComp.getBlocks().remove(tempBlockItem);
            }
        } else {
            List<InitDeclarator> initDeclarators = declaration.getInitDeclaratorList().getInitDeclarators();
            Iterator<InitDeclarator> iterator = initDeclarators.iterator();

            // Safely iterate through and remove unused variables using iterator
            while (iterator.hasNext()) {
                InitDeclarator init = iterator.next();
                if (init.getDeclarator().getDirectDeclarator() instanceof IdentifierDeclarator) {
                    String varName = ((IdentifierDeclarator) init.getDeclarator().getDirectDeclarator()).getName();
                    if (unusedVariables.contains(varName + "&" + tempDecLine)) {
                        setChange(Boolean.TRUE);
                        iterator.remove();  // Safely remove with iterator's remove
                    }
                }
            }

            // If the list is empty after removal, remove the external declaration
            if (initDeclarators.size() == 0) {
                tempFunc.getCompoundStatement().getBlocks().remove(tempBlockItem);
            }
        }
    }

    @Override
    public Void visit(Program program) {
        program.getTranslationUnit().accept(this);
        return null;
    }

    @Override
    public Void visit(TranslationUnit translationUnit) {
        tempTranslation = translationUnit;

        // makeing a copy from the original version
        List<ExternalDeclaration> originalList = new ArrayList<>(translationUnit.getExternalDeclarations());

        // traverse on the copy version
        for (ExternalDeclaration ext : originalList) {
            tempExtDec = ext;
            if(ext instanceof FuncDefExtDec)
                ext.accept(this); //here if needed, the original item remove
        }

        return null;
    }


    @Override
    public Void visit(FuncDefExtDec funcDefExtDec) {
        // Function parameters processing
        tempFunc = funcDefExtDec;
        tempFuncLine = funcDefExtDec.getLine();

        if (funcDefExtDec.getDeclarator() != null)
            funcDefExtDec.getDeclarator().accept(this);

        // Update number of arguments by removing unused ones
        if(toRemoveIndices != null){
            funcDefExtDec.setNewNumOFArgs(funcDefExtDec.getNumOfArgs() - toRemoveIndices.size());
            changedFunctions.put(tempFuncNames + "-" + funcDefExtDec.getNumOfArgs(), toRemoveIndices);
        }
        this.DeclaredFunctions.add(tempFuncNames+"-"+funcDefExtDec.getNewNumOFArgs());

        // Process the function body (compound statement)
        funcDefExtDec.getCompoundStatement().accept(this);
        return null;
    }

    @Override
    public Void visit(TypeSpecifier typeSpecifier) {
        return null;
    }

    @Override
    public Void visit(DecExtDec declaration) {
        tempDecLine = declaration.getDeclarationSpecifiers().getLine();
        checkDeclaration(declaration);
        return null;
    }

    @Override
    public Void visit(DeclarationSpecifiers declarationSpecifiers) {
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

//    @Override
//    public Void visit(CompoundStatement compoundStatement) {
//        tempComp = compoundStatement;
//        for (BlockItem bl : compoundStatement.getBlocks()) {
//            if (bl.getDeclaration() != null) {
//                tempBlockItem = bl;
//                bl.getDeclaration().accept(this);
//            }
//            if (bl.getStatement() != null) {
//                bl.getStatement().accept(this);
//            }
//        }
//        return null;
//    }
    @Override
    public Void visit(CompoundStatement compoundStatement) {
        tempComp = compoundStatement;

        // Create a safe copy of the block list to iterate
        List<BlockItem> blockCopy = new ArrayList<>(compoundStatement.getBlocks());

        for (BlockItem bl : blockCopy) {
            if (bl.getDeclaration() != null) {
                tempBlockItem = bl;
                bl.getDeclaration().accept(this);
            }
            if (bl.getStatement() != null) {
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
    public Void visit(ParameterList parameterList) {
        ArrayList<ParameterDeclaration> params = parameterList.getParameters();
        List<Integer> tempToRemoveIndices = new ArrayList<>();

        // Collect indices of unused parameters
        for (int i = 0; i < params.size(); i++) {
            String parName = params.get(i).getSpecifiers().getDeclarationSpecifiers().get(1).getName();
            if (unusedVariables.contains(parName + "&" + tempFuncLine)) {
                tempToRemoveIndices.add(i);
            }
        }

        // Remove the unused parameters after collecting indices
        toRemoveIndices = tempToRemoveIndices;
        for (int i = tempToRemoveIndices.size() - 1; i >= 0; i--) {
            setChange(Boolean.TRUE);
            params.remove((int) tempToRemoveIndices.get(i));
        }

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
    public String visit(IdentifierDeclarator identifierDeclarator) {
        if(identifierDeclarator.getName() != null)
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
        if (functionDeclarator.getParameterList() != null)
            functionDeclarator.getParameterList().accept(this);
        return null;
    }

    @Override
    public Void visit(InitDeclaratorList initDeclaratorList) {
        // Iterate through the initializers safely
        for (InitDeclarator init : initDeclaratorList.getInitDeclarators()) {
            if (init != null) {
                if (init.getInitializer() != null)
                    init.getInitializer().accept(this);
            }
            if (init.getDeclarator() != null)
                init.getDeclarator().accept(this);
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
        //to do
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
