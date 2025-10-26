package main.ast.nodes.Expr;
import main.ast.nodes.Expr.operator.AssignmentOperator;
import main.visitor.IVisitor;

public class AssignmentExpr extends Expr {
    private Expr leftExpr;
    private Expr rightExpr;
    private String assignmentOperator;

    public AssignmentExpr(Expr leftExpr, String operator, Expr rightExpr) {
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
        this.assignmentOperator = operator;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expr getLeftExpr() {
        return leftExpr;
    }

    public void setLeftExpr(Expr leftExpr) {
        this.leftExpr = leftExpr;
    }

    public Expr getRightExpr() {
        return rightExpr;
    }

    public void setRightExpr(Expr secondOperand) {
        this.rightExpr = rightExpr;
    }

    public String getOperator() {
        return assignmentOperator;
    }

    public void setOperator(String operator) {
        this.assignmentOperator = assignmentOperator;
    }
}
