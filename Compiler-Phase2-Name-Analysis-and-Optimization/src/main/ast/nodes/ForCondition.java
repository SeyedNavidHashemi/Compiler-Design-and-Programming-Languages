package main.ast.nodes;
import main.ast.nodes.Expr.Expr;
import main.ast.nodes.ExternalDeclaration.DecExtDec;
import main.visitor.IVisitor;

public class ForCondition extends Node {

    private ForDeclaration forDeclaration;      // OR
    private Expr initExpression;       // Only one of these will be non-null
    private ForExpr conditionExpression;
    private ForExpr updateExpression;

    public ForDeclaration getForDeclaration() {
        return forDeclaration;
    }

    public Expr getInitExpression() {
        return initExpression;
    }

    public ForExpr getConditionExpression() {
        return conditionExpression;
    }

    public ForExpr getUpdateExpression() {
        return updateExpression;
    }

    public ForCondition() {}

    public void setUpdateExpression(ForExpr updateExpression) {
        this.updateExpression = updateExpression;
    }

    public void setConditionExpression(ForExpr conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public void setInitExpression(Expr initExpression) {
        this.initExpression = initExpression;
    }

    public void setForDeclaration(ForDeclaration forDeclaration) {
        this.forDeclaration = forDeclaration;
    }



    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

