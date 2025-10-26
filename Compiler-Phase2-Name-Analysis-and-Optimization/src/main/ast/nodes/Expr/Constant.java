package main.ast.nodes.Expr;
import main.visitor.IVisitor;

public class Constant extends Expr{
    public String getConstantValue() {
        return constantValue;
    }

    private String constantValue;

    public Constant(String _vaule) {this.constantValue = _vaule;}

    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}