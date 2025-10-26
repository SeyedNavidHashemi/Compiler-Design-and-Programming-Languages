package main.ast.nodes.Stmt;
import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

public class ExpressionStatement extends Stmt {
   private Expr exprStmt;

    public ExpressionStatement() {}

    public void setExpr(Expr exprStmt) {
        this.exprStmt = exprStmt;
    }

    public Expr getExprStmt() {
        return exprStmt;
    }

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
