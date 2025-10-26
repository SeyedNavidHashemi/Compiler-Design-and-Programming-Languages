package main.ast.nodes.Expr;
import main.ast.nodes.TypeName;
import main.visitor.IVisitor;

public class SizeOfExpr extends Expr{
    private String sizeOf;
    private TypeName type;

    public SizeOfExpr(String sizeOf, TypeName type)
    {
        this.sizeOf = sizeOf;
        this.type = type;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}