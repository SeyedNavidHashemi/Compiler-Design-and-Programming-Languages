package main.ast.nodes;
import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

public class Designator extends Node {

    public Expr getExpr() {
        return expr;
    }

    private Expr expr;
    private String fieldName;

    public Designator(){}

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

