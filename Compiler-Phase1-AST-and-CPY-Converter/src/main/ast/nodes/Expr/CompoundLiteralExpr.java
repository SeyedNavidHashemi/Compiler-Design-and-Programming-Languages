package main.ast.nodes.Expr;
import main.ast.nodes.InitializerList;
import main.ast.nodes.TypeName;
import main.visitor.IVisitor;

public class CompoundLiteralExpr extends Expr{
    private TypeName type;
    private InitializerList initializerList;

    public CompoundLiteralExpr(TypeName type, InitializerList initializerList)
    {
        this.type = type;
        this.initializerList = initializerList;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}