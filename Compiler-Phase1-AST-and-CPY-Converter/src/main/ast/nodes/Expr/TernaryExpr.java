package main.ast.nodes.Expr;
import main.visitor.IVisitor;

public class TernaryExpr extends Expr {
    private Expr condition;
    private Expr thenExpr;
    private Expr elseExpr;

    public TernaryExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }
    public Expr getThenExpr() {
        return thenExpr;
    }

    public Expr getCondition() {
        return condition;
    }

    public Expr getElseExpr() {
        return elseExpr;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
