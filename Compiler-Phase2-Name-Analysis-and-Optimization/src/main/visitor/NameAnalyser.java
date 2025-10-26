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
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemAlreadyExistsException;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.item.FuncDecSymbolTableItem;
import main.symbolTable.item.SymbolTableItem;
import main.symbolTable.item.VarDecSymbolTableItem;

import java.util.*;

/*GOALs:
 *   1. this is for setting symbol table
 * */

public class NameAnalyser extends Visitor<Void>{
    ArrayList<String> unusedVariables = new ArrayList<>();
    ArrayList<String> DeclaredFunctions = new ArrayList<>();
    List<String> builtInFunctions = Arrays.asList("printf", "scanf");
    String tempFuncNames;
    int tempDecLine;
    int state = 0;
    String tempType;
    private FuncDefExtDec main;
    private int mainNumOfArgs = 0;
    String arrayType;
    private Boolean Error = Boolean.FALSE;

    public Boolean getError() {
        return Error;
    }

    public void setError(Boolean error) {
        Error = error;
    }

    public void updateUnusedVariables(SymbolTable topSymbolTable){
        for (Map.Entry<String, SymbolTableItem> entry : topSymbolTable.items.entrySet()) {
            SymbolTableItem item = entry.getValue();
            if (item instanceof VarDecSymbolTableItem) {
                if(((VarDecSymbolTableItem) item).getUsed() == 0){
                    unusedVariables.add(((VarDecSymbolTableItem) item).getVarName() + "&" + ((VarDecSymbolTableItem) item).getLine());}
//                System.out.println(((VarDecSymbolTableItem) item).getVarName() + "&" + ((VarDecSymbolTableItem) item).getLine());
            }
        }

    }

    public int getMainNumOfArgs() {
        return mainNumOfArgs;
    }


    public ArrayList<String> getDeclaredFunctions() {
        return DeclaredFunctions;
    }

    public FuncDefExtDec getMain() {
        return this.main;
    }

    public ArrayList<String> getUnusedVariables(){
        return unusedVariables;
    }

    @Override
    public Void visit(Program program) {
        SymbolTable.top = new SymbolTable();
        SymbolTable.root = SymbolTable.top;

        program.setSymbol_table(SymbolTable.top);
        program.getTranslationUnit().accept(this);

        return null;
    }

    @Override
    public Void visit(TranslationUnit translationUnit){
        for(ExternalDeclaration ext : translationUnit.getExternalDeclarations()){
            if(ext != null)
                ext.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(FuncDefExtDec funcDefExtDec) {
        int numOfArgs = 0;
        state = 0;
        if(funcDefExtDec.getDeclarator().getDirectDeclarator() instanceof FunctionDeclarator) {
            funcDefExtDec.getDeclarator().getDirectDeclarator().accept(this);
            if(((FunctionDeclarator) funcDefExtDec.getDeclarator().getDirectDeclarator()).getParameterList() != null)
                numOfArgs = ((FunctionDeclarator) funcDefExtDec.getDeclarator().getDirectDeclarator()).getParameterList().getParameters().size();
        }
        if(Objects.equals(tempFuncNames, "main")) {
            this.main = funcDefExtDec;
            this.mainNumOfArgs = numOfArgs;
        }
        funcDefExtDec.setNumOfArgs(numOfArgs);
        state = 1;
        FuncDecSymbolTableItem func_dec_item = new FuncDecSymbolTableItem(funcDefExtDec, tempFuncNames, numOfArgs);
        try {
            SymbolTable.top.put(func_dec_item);
            this.DeclaredFunctions.add(tempFuncNames+"-"+numOfArgs);
        } catch (ItemAlreadyExistsException e) {
            System.out.println("Redefinition of function \"" + tempFuncNames +"\" in line " + funcDefExtDec.getLine());
            setError(Boolean.TRUE);
        }

        SymbolTable func_dec_symbol_table = new SymbolTable(SymbolTable.top);
        funcDefExtDec.setSymbol_table(func_dec_symbol_table);
        SymbolTable.push(func_dec_symbol_table);

        //visit declarator(parameters)
        if(funcDefExtDec.getDeclarator() != null)
            funcDefExtDec.getDeclarator().accept(this);

        //visit declarationList
        if(funcDefExtDec.getDeclarationList() != null)
            funcDefExtDec.getDeclarationList().accept(this);

        //visit compoundStmt
        if(funcDefExtDec.getCompoundStatement() != null)
            funcDefExtDec.getCompoundStatement().accept(this);

        updateUnusedVariables(SymbolTable.top);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(TypeSpecifier typeSpecifier) {
        return null;
    }

    @Override
    public Void visit(DeclarationSpecifiers declarationSpecifiers) {
        if(declarationSpecifiers.getDeclarationSpecifiers() != null)
        {
            if(declarationSpecifiers.getDeclarationSpecifiers().size() == 2) {
                String varType = declarationSpecifiers.getDeclarationSpecifiers().get(0).getName();
                String varName = declarationSpecifiers.getDeclarationSpecifiers().get(1).getName();
                VarDecSymbolTableItem var_dec_item = new VarDecSymbolTableItem(varType, varName, declarationSpecifiers.getLine());

                try {
                    SymbolTable.top.put(var_dec_item);
//                    System.out.println("Variable "+ varType + " "+ varName + " declared in line "+ declarationSpecifiers.getLine());
                } catch (ItemAlreadyExistsException e) {
                    System.out.println("Redeclaration of variable \"" + varName + "\" in line " + declarationSpecifiers.getLine());
                    setError(Boolean.TRUE);
                }
            }
        }
        return null;
    }

    public ArrayList<String> fetchNewVariables(InitDeclaratorList initDeclaratorList){
        ArrayList<String> newVars = new ArrayList<>();
        for(InitDeclarator init : initDeclaratorList.getInitDeclarators()){
            if((init.getDeclarator() != null) && (init.getDeclarator().getDirectDeclarator() instanceof IdentifierDeclarator)){
                String newVar = ((IdentifierDeclarator) init.getDeclarator().getDirectDeclarator()).getName();
                newVars.add(newVar);
            }
            if((init.getDeclarator() != null) && (init.getDeclarator().getDirectDeclarator() instanceof ArrayDeclarator)){
                if(((ArrayDeclarator) init.getDeclarator().getDirectDeclarator()).getBaseDeclarator() instanceof IdentifierDeclarator) {
                    String newVar = ((IdentifierDeclarator) ((ArrayDeclarator) init.getDeclarator().getDirectDeclarator()).getBaseDeclarator()).getName();
                    newVars.add(newVar);
                }
            }
        }
        return newVars;
    }

    public void addNewVarDeclarations(String varType, ArrayList<String> varNames, int line){
        for(String newVarName : varNames) {
            VarDecSymbolTableItem var_dec_item = new VarDecSymbolTableItem(varType, newVarName, line);

            try {
                SymbolTable.top.put(var_dec_item);
//                System.out.println("VAR "+ varType + " "+ newVarName + " declared");
            } catch (ItemAlreadyExistsException e) {
                System.out.println("Redeclaration of variable \"" + newVarName + "\" in line " + line);
                setError(Boolean.TRUE);
            }
        }
    }

    @Override
    public Void visit(DecExtDec declaration){
        if(declaration.getDeclarationSpecifiers() != null)
            declaration.getDeclarationSpecifiers().accept(this);
        ArrayList<String> newVariables = new ArrayList<>();
        if(declaration.getInitDeclaratorList() != null)
        {
            newVariables = fetchNewVariables(declaration.getInitDeclaratorList());
            int line = declaration.getDeclarationSpecifiers().getLine();
            addNewVarDeclarations(tempType, newVariables, line);
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
        for(DecExtDec dec : declarationList.getDeclarations()){
            if(dec != null)
                dec.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(BlockItem blockItem){
        return null;
    }

    @Override
    public Void visit(CompoundStatement compoundStatement) {
        for(BlockItem bl : compoundStatement.getBlocks())
        {
            if(bl != null) {
                if (bl.getDeclaration() != null)
                    bl.getDeclaration().accept(this);
                else if(bl.getStatement() != null)
                    bl.getStatement().accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visit(Identifier identifier) {
        try {
//            SymbolTable.top.getItem(VarDecSymbolTableItem.START_KEY + identifier.getName());
            String key = VarDecSymbolTableItem.START_KEY + identifier.getName();
            SymbolTableItem item = SymbolTable.top.getItem(key);

            if (item instanceof VarDecSymbolTableItem) {
                VarDecSymbolTableItem varItem = (VarDecSymbolTableItem) item;
                varItem.setUsed(1);
            }
        } catch (ItemNotFoundException e) {
            System.out.println("Line:" + identifier.getLine() + "-> " + identifier.getName() + " not declared");
            setError(Boolean.TRUE);
        }
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
        if(declarationSpecifier != null)
            tempType = declarationSpecifier.getName();
        return null;
    }

    @Override
    public Void visit(Pointer pointer) {
        return null;
    }

    @Override
    public Void visit(NestedDeclarator nestedDeclarator) {
        nestedDeclarator.getInnerDeclarator().accept(this);
        return null;
    }

    @Override
    public String visit(IdentifierDeclarator identifierDeclarator) {
        tempFuncNames = identifierDeclarator.getName();
        return null;
    }

    @Override
    public Void visit(ArrayDeclarator arrayDeclarator) {
//        System.out.println("This is for Array declaration");
        if(arrayDeclarator.getBaseDeclarator() != null)
            arrayDeclarator.getBaseDeclarator().accept(this);
        if(arrayDeclarator.getExpr() != null)
            arrayDeclarator.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(FunctionDeclarator functionDeclarator) {
        if(state == 0) {
            if (functionDeclarator.getBaseDirectDeclarator() instanceof IdentifierDeclarator)
                tempFuncNames = ((IdentifierDeclarator) functionDeclarator.getBaseDirectDeclarator()).getName();
        }
        else {
            if(functionDeclarator.getParameterList() != null)
                functionDeclarator.getParameterList().accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ParameterList parameterList) {
        for(ParameterDeclaration par : parameterList.getParameters())
        {
            if(par != null)
                par.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {
        if(parameterDeclaration.getSpecifiers() != null)
            parameterDeclaration.getSpecifiers().accept(this);
        if(parameterDeclaration.getDeclarator() != null)
            parameterDeclaration.getDeclarator().accept(this);
        //abstract remained
        return null;
    }

    @Override
    public Void visit(IdentifierList identifierList) {
        for(Identifier id : identifierList.getIdentifiers()){
            if(id != null)
                id.accept(this);
        }
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
        SymbolTable symbolTable = new SymbolTable(SymbolTable.top);
        iterationStatement.setSymbol_table(symbolTable);
        SymbolTable.push(symbolTable);

        //visit expr and stmt
        if(iterationStatement.getCondition() != null)
            iterationStatement.getCondition().accept(this);
        if(iterationStatement.getForCondition() != null)
            iterationStatement.getForCondition().accept(this);
        if(iterationStatement.getStatement() != null)
            iterationStatement.getStatement().accept(this);

        updateUnusedVariables(SymbolTable.top);
        SymbolTable.pop();
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
        SymbolTable symbolTable = new SymbolTable(SymbolTable.top);
        selectionStatement.setIfSymbolTable(symbolTable);
        SymbolTable.push(symbolTable);

        //visit if
        selectionStatement.getCondition().accept(this);
        selectionStatement.getThenStmt().accept(this);

        updateUnusedVariables(SymbolTable.top);
        SymbolTable.pop();

        //visit else if
        for (int i = 0; i < selectionStatement.getElseIfThenStmts().size(); i++) {
            SymbolTable symbolTable1 = new SymbolTable(SymbolTable.top);
            selectionStatement.addElseIfSymbolTable(symbolTable1);
            SymbolTable.push(symbolTable1);

            Stmt stmt = selectionStatement.getElseIfThenStmts().get(i);
            if(stmt != null)
                stmt.accept(this);

            updateUnusedVariables(SymbolTable.top);
            SymbolTable.pop();
        }

        SymbolTable symbolTable2 = new SymbolTable(SymbolTable.top);
        selectionStatement.setElseSymbolTale(symbolTable2);
        SymbolTable.push(symbolTable2);

        //visit else
        if(selectionStatement.getElseStmt() != null)
            selectionStatement.getElseStmt().accept(this);


        updateUnusedVariables(SymbolTable.top);
        SymbolTable.pop();


        return null;
    }

    @Override
    public Void visit(ForCondition forCondition) {
        if(forCondition.getForDeclaration() != null)
            forCondition.getForDeclaration().accept(this);
        if(forCondition.getInitExpression() != null)
            forCondition.getInitExpression().accept(this);
        return null;
    }

    @Override
    public Void visit(ForExpr forExpr) {
        for(Expr newExpr : forExpr.getExpressions()){
            if(newExpr != null)
                newExpr.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ForDeclaration forDeclaration) {
        if(forDeclaration.getDeclarationSpecifiers() != null)
            forDeclaration.getDeclarationSpecifiers().accept(this);
        ArrayList<String> newVariables = new ArrayList<>();
        if(forDeclaration.getInitDeclaratorList() != null)
        {
            newVariables = fetchNewVariables(forDeclaration.getInitDeclaratorList());
            int line = forDeclaration.getDeclarationSpecifiers().getLine();
            addNewVarDeclarations(tempType, newVariables, line);
        }
        return null;
    }

    @Override
    public Void visit(InitDeclaratorList initDeclaratorList) {
        for(InitDeclarator init : initDeclaratorList.getInitDeclarators())
        {
            if(init != null)
            {
                init.accept(this);
            }
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
        jumpStatement.getReturnExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(TypeName typeName) {
        return null;
    }

    @Override
    public Void visit(CompoundLiteralExpr compoundLiteralExpr) {
        if (compoundLiteralExpr.getInitializerList() != null) {
            compoundLiteralExpr.getInitializerList().accept(this);
        }
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
        String curName = ((Identifier)((functionCallExpr).getExpr())).getName();
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
        //checking declaration of arguments
        if(functionCallExpr.getArgumentExpressionList() != null)
            functionCallExpr.getArgumentExpressionList().accept(this);


        try {
            if (!builtInFunctions.contains(curName)) {
                SymbolTable.top.getItem(FuncDecSymbolTableItem.START_KEY + curName+"-"+numOfArgs);
            }
        } catch (ItemNotFoundException e) {
            System.out.println("Line:" + functionCallExpr.getLine() + "-> " + curName + " not declared");
            setError(Boolean.TRUE);
        }
        return null;
    }

    @Override
    public Void visit(CastExpr castExpr) {
        if (castExpr.getExpr() != null) {
            castExpr.getExpr().accept(this);
        }
        return null;
    }


    @Override
    public Void visit(PrefixUnaryExpr prefixUnaryExpr) {
        if(prefixUnaryExpr.getOperand() != null)
            prefixUnaryExpr.getOperand().accept(this);
        return null;
    }

    @Override
    public Void visit(SizeOfExpr sizeOfExpr) {

        return null;
    }
}


