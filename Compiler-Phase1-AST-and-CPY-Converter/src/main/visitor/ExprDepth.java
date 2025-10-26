//package main.visitor;
//
//import main.ast.nodes.*;
//import main.ast.nodes.DirectDeclarator.ArrayDeclarator;
//import main.ast.nodes.DirectDeclarator.FunctionDeclarator;
//import main.ast.nodes.DirectDeclarator.IdentifierDeclarator;
//import main.ast.nodes.DirectDeclarator.NestedDeclarator;
//import main.ast.nodes.Expr.*;
//import main.ast.nodes.Expr.operator.BinaryOperator;
//import main.ast.nodes.ExternalDeclaration.*;
//import main.ast.nodes.Stmt.*;
//
///*GOALs:
// *   1. print out number of statements in each scope
// * */
//
//
//public class ExprDepth extends Visitor<Void>{
//    @Override
//    public Void visit(Program program) {
//        program.getTranslationUnit().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(TranslationUnit translationUnit){
//        for(ExternalDeclaration ext : translationUnit.getExternalDeclarations()){
//            if(ext instanceof FuncDefExtDec) {
//                ext.accept(this);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(FuncDefExtDec funcDefExtDec) {
//        funcDefExtDec.getCompoundStatement().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(DecExtDec decExtDec){
//        if(decExtDec.getInitDeclaratorList() != null)
//            decExtDec.getInitDeclaratorList().accept(this);
//        return null;
//    }
//    @Override
//    public Void visit(DeclarationSpecifiers declarationSpecifiers) {
//        return null;
//    }
//
//    @Override
//    public Void visit(Declarator declarator) {
//        declarator.getDirectDeclarator().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(DeclarationList declarationList) {
//        return null;
//    }
//
//    @Override
//    public Void visit(BlockItem blockItem){
//        return null;
//    }
//
//    @Override
//    public Void visit(CompoundStatement compoundStatement) {
//        for (BlockItem block : compoundStatement.getBlocks()) {
//            if(block.getStatement() != null){
//                block.getStatement().accept(this);
//            }
//            if(block.getDeclaration() != null){
//                block.getDeclaration().accept(this);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(Identifier identifier) {
//        return null;
//    }
//
//    @Override
//    public Void visit(Constant constant) {
//        return null;
//    }
//
//    private int calcDepth(BinaryExpr expr)
//    {
//        int leftDepth = 0;
//        int rightDepth = 0;
//        if (expr.getFirstOperand() != null)
//        {
//            if(expr.getFirstOperand() instanceof Identifier || expr.getFirstOperand() instanceof Constant)
//                leftDepth += 0;
//            else if(expr.getFirstOperand() instanceof UnaryExpr)
//                leftDepth = calcDepth1((UnaryExpr) expr.getFirstOperand());
////            else if(expr.getFirstOperand() instanceof CommaExpr)
////                leftDepth = handleList((ListOfExpressions) expr.getFirstExpression());
//            else
//                leftDepth = calcDepth((BinaryExpr) expr.getFirstOperand());
//        }
//
//        if (expr.getSecondOperand() != null)
//        {
//            if(expr.getSecondOperand() instanceof Identifier || expr.getSecondOperand() instanceof Constant)
//                rightDepth += 0;
//            else if(expr.getSecondOperand() instanceof UnaryExpr)
//                rightDepth = calcDepth1((UnaryExpr) expr.getSecondOperand());
////            else if(expr.getSecondOperand() instanceof ListOfExpressions)
////                rightDepth = handleList((ListOfExpressions) expr.getSecondExpression());
//            else
//                rightDepth = calcDepth((BinaryExpr) expr.getSecondOperand());
//        }
//
//        return 1 + Math.max(leftDepth, rightDepth);}
//
//
//
//    @Override
//    public Void visit(BinaryExpr binaryExpr) {
//        int max = calcDepth(binaryExpr);
//        BinaryOperator operator = binaryExpr.getOperator();
//        System.out.println("maximum depth for " + operator + " is: "+ max);
//        binaryExpr.getFirstOperand().accept(this);
//        binaryExpr.getSecondOperand().accept(this);
//        return null;
//    }
//
//    private int calcDepth1(UnaryExpr expr) {
//        int innerDepth = 0;
//        if (expr.getOperand() != null)
//        {
//            if(expr.getOperand() instanceof Identifier || expr.getOperand() instanceof Constant)
//                innerDepth += 0;
//            else if(expr.getOperand() instanceof BinaryExpr)
//                innerDepth = calcDepth((BinaryExpr) expr.getOperand());
//            else
//                innerDepth = calcDepth1((UnaryExpr) expr.getOperand());
//        }
//
//        return 1 + innerDepth;}
//
//    @Override
//    public Void visit(UnaryExpr unaryExpr)
//    {
//        int max = calcDepth1(unaryExpr);
//        String operator = unaryExpr.getOperator();
//        System.out.println("U maximum depth for " + operator + " is: "+ max);
//        unaryExpr.getOperand().accept(this);
//        return null;}
//
//    @Override
//    public Void visit(TernaryExpr ternaryExpr) {
//        ternaryExpr.getCondition().accept(this);
//        ternaryExpr.getThenExpr().accept(this);
//        ternaryExpr.getElseExpr().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(CommaExpr commaExpr) {
//        return null;
//    }
//
//    @Override
//    public Void visit(AssignmentExpr assignmentExpr) {
//        assignmentExpr.getRightExpr().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(DeclarationSpecifier declarationSpecifier) {
//        return null;
//    }
//
//    @Override
//    public Void visit(Pointer pointer) {
//        return null;
//    }
//
//    @Override
//    public Void visit(NestedDeclarator nestedDeclarator) {
//        return null;
//    }
//
//    @Override
//    public String visit(IdentifierDeclarator identifierDeclarator) {
//        return null;
//    }
//
//    @Override
//    public Void visit(ArrayDeclarator arrayDeclarator) {
//        if(arrayDeclarator.getExpr() != null)
//            arrayDeclarator.getExpr().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(FunctionDeclarator functionDeclarator) {
//        return null;
//    }
//
//    @Override
//    public Void visit(ParameterList parameterList) {
//        return null;
//    }
//
//    @Override
//    public Void visit(ParameterDeclaration parameterDeclaration) {
//        return null;
//    }
//
//    @Override
//    public Void visit(IdentifierList identifierList) {
//        return null;
//    }
//
//    @Override
//    public Void visit(AbstractDeclarator abstractDeclarator) {
//        return null;
//    }
//
//    @Override
//    public Void visit(DirectAbstractDeclarator directAbstractDeclarator) {
//        return null;
//    }
//
//    @Override
//    public Void visit(StringLiteral stringLiteral) {
//        return null;
//    }
//
//    @Override
//    public Void visit(SpecifierQualifierList specifierQualifierList) {
//        return null;
//    }
//
//    @Override
//    public Void visit(IterationStatement iterationStatement) {
//        if(iterationStatement.getCondition() != null)
//        {
//            iterationStatement.getCondition().accept(this);
//        }
//        if(iterationStatement.getForCondition() != null)
//        {
//            iterationStatement.getForCondition().accept(this);
//        }
//        if(iterationStatement.getStatement() != null)
//        {
//            iterationStatement.getStatement().accept(this);
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(ExpressionStatement expressionStatement) {
//        if(expressionStatement.getExprStmt() != null)
//        {
//            expressionStatement.getExprStmt().accept(this);
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(SelectionStatement selectionStatement) {
//        if(selectionStatement.getThenStmt() != null)
//        {
//            selectionStatement.getThenStmt().accept(this);
//        }
//        for(Stmt stmt : selectionStatement.getElseIfThenStmts())
//        {
//            if(stmt != null)
//                stmt.accept(this);
//        }
//        if(selectionStatement.getElseStmt() != null)
//        {
//            selectionStatement.getElseStmt().accept(this);
//        }
//            return null;
//    }
//
//    @Override
//    public Void visit(ForCondition forCondition) {
//        return null;
//    }
//
//    @Override
//    public Void visit(ForExpr forExpr) {
//        for(Expr expr : forExpr.getExpressions())
//        {
//            expr.accept(this);
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(ForDeclaration forDeclaration) {
//        return null;
//    }
//
//    @Override
//    public Void visit(InitDeclaratorList initDeclaratorList) {
//        for(InitDeclarator init : initDeclaratorList.getInitDeclarators())
//        {
//            init.accept(this);
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(InitDeclarator initDeclarator) {
//        initDeclarator.getDeclarator().accept(this);
//        if(initDeclarator.getInitializer() != null)
//            initDeclarator.getInitializer().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(Initializer initializer) {
//        if(initializer.getExpr() != null)
//            initializer.getExpr().accept(this);
//        if(initializer.getInitializerList() != null)
//            initializer.getInitializerList().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(DesignationInitializerTuple designationInitializerTuple) {
//        return null;
//    }
//
//    @Override
//    public Void visit(InitializerList initializerList) {
//        for(Designation desig : initializerList.getDesignationList())
//        {
//            desig.accept(this);
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(Designation designation) {
//        for(Designator des : designation.getDesignators())
//        {
//            des.accept(this);
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(Designator designator) {
//        if(designator.getExpr() != null)
//            designator.getExpr().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(JumpStatement jumpStatement) {
//        if(jumpStatement.getReturnExpr() != null)
//        {
//            jumpStatement.getReturnExpr().accept(this);
//        }
//        return null;
//    }
//
//    @Override
//    public Void visit(TypeName typeName) {
//        return null;
//    }
//
//    @Override
//    public Void visit(CompoundLiteralExpr compoundLiteralExpr) {
//        return null;
//    }
//
//    @Override
//    public Void visit(ArrayIndexingExpr arrayIndexingExpr) {
//        if(arrayIndexingExpr.getArrayExpr() != null)
//            arrayIndexingExpr.getArrayExpr().accept(this);
//        if(arrayIndexingExpr.getIndexExpr() != null)
//            arrayIndexingExpr.getIndexExpr().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(FunctionCallExpr functionCallExpr) {
//        return null;
//    }
//
//    @Override
//    public Void visit(CastExpr castExpr) {
//        if(castExpr.getExpr() != null)
//            castExpr.getExpr().accept(this);
//        return null;
//    }
//
//    @Override
//    public Void visit(PrefixUnaryExpr prefixUnaryExpr) {
//        return null;
//    }
//
//    @Override
//    public Void visit(SizeOfExpr sizeOfExpr) {
//        return null;
//    }
//}
//
//
//
//
