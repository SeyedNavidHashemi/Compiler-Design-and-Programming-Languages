package main.ast.nodes.Expr;
import main.ast.nodes.Expr.operator.UnaryOperator;
import main.visitor.IVisitor;

public class UnaryExpr extends Expr{
    private Expr operand;
    private String operator;


    public UnaryExpr(Expr operand, String operator)
    {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expr getOperand() {
        return operand;
    }

    public void setOperand(Expr operand) {
        this.operand = operand;
    }

    public String getOperator() {return operator;}

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
