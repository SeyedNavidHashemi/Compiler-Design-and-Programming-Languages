package main.ast.nodes.Expr;
import main.visitor.IVisitor;

public class StringLiteral extends Expr{
    private String stringLiteralValue;

    public StringLiteral(String value) {this.stringLiteralValue = value;}
    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}