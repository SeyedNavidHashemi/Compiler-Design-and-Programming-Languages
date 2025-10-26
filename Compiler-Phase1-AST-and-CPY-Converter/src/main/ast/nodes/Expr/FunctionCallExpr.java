package main.ast.nodes.Expr;
import main.visitor.IVisitor;

public class FunctionCallExpr extends Expr{
    public Expr getExpr() {
        return expr;
    }

    private Expr expr;

    public CommaExpr getArgumentExpressionList() {
        return argumentExpressionList;
    }

    private CommaExpr argumentExpressionList;

    public FunctionCallExpr(Expr expr) {this.expr = expr;}

    public void setArgumentExpressionList(CommaExpr argumentExpressionList) {
        this.argumentExpressionList = argumentExpressionList;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}