package main.ast.nodes;
import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

public class Initializer extends Node {

    private Expr expr;
    private InitializerList initializerList;

    public Expr getExpr() {
        return expr;
    }

    public InitializerList getInitializerList() {
        return initializerList;
    }

    public Initializer() {}

    public void setInitializerList(InitializerList initializerList) {
        this.initializerList = initializerList;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }


    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

