package main.ast.nodes.Expr;
import main.visitor.IVisitor;

public class ArrayIndexingExpr extends Expr{
    public Expr getArrayExpr() {
        return arrayExpr;
    }

    public Expr getIndexExpr() {
        return indexExpr;
    }

    private Expr arrayExpr;
    private Expr indexExpr;

    public ArrayIndexingExpr(Expr arrayExpr, Expr indexExpr)
    {
        this.arrayExpr = arrayExpr;
        this.indexExpr = indexExpr;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}