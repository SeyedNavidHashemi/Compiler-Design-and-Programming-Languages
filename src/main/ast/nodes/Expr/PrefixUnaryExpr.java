package main.ast.nodes.Expr;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class PrefixUnaryExpr extends Expr{
    private ArrayList<String> prefixOperators = new ArrayList<>();
    private Expr operand;


    public PrefixUnaryExpr() {}

    public void setOperand(Expr operand) {
        this.operand = operand;
    }
    public void addPrefixOperator(String prefixOperator)
    {
        this.prefixOperators.add(prefixOperator);
    }

    public Expr getOperand() {
        return operand;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}