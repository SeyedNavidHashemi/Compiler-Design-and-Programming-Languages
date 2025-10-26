package main.ast.nodes.Expr;
import main.ast.nodes.Expr.operator.BinaryOperator;
import main.visitor.IVisitor;

public class BinaryExpr extends Expr {
    private Expr firstOperand;
    private Expr secondOperand;
    private String binaryOperator;

    public BinaryExpr(Expr firstOperand, String operator, Expr secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.binaryOperator = operator;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expr getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(Expr firstOperand) {
        this.firstOperand = firstOperand;
    }

    public Expr getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(Expr secondOperand) {
        this.secondOperand = secondOperand;
    }

    public String getOperator() {
        return binaryOperator;
    }

    public void setOperator(String operator) {
        this.binaryOperator = operator;
    }
}
