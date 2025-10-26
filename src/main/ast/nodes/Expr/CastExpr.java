package main.ast.nodes.Expr;
import main.ast.nodes.Node;
import main.ast.nodes.TypeName;
import main.visitor.IVisitor;

public class CastExpr extends Expr {
    private TypeName type;

    public Expr getExpr() {
        return expr;
    }

    private Expr expr;

    public CastExpr(TypeName type, Expr expr) {
        this.type = type;
        this.expr = expr;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}
}
