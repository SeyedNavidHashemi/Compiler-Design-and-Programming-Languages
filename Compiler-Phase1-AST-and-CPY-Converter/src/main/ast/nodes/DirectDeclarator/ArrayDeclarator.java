package main.ast.nodes.DirectDeclarator;
import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

public class ArrayDeclarator extends DirectDeclarator {
    private DirectDeclarator baseDeclarator;

    public Expr getExpr() {
        return expr;
    }

    private Expr expr;

    public ArrayDeclarator(DirectDeclarator _baseDeclarator) {this.baseDeclarator = _baseDeclarator;}

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}