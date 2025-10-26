package main.ast.nodes.Stmt;
import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

public class JumpStatement extends Stmt {

    private String jumpType;
    private Expr returnExpr;

    public JumpStatement() {}

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public void setReturnExpr(Expr returnExpr) {
        this.returnExpr = returnExpr;
    }

    public Expr getReturnExpr() {
        return returnExpr;
    }

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
