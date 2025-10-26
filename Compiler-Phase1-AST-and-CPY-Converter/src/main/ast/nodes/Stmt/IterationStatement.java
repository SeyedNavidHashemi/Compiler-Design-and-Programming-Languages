package main.ast.nodes.Stmt;
import main.ast.nodes.Expr.Expr;
import main.ast.nodes.ForCondition;
import main.visitor.IVisitor;

public class IterationStatement extends Stmt {
    private String type;
    private Expr condition;
    private Stmt statement;
    private ForCondition forCondition;

    public IterationStatement() {}

    public void setType(String type) {
        this.type = type;
    }

    public void setCondition(Expr condition) {
        this.condition = condition;
    }

    public void setStatement(Stmt statement) {
        this.statement = statement;
    }

    public void setForCondition(ForCondition forCondition) {
        this.forCondition = forCondition;
    }

    public Stmt getStatement() {
        return statement;
    }


    public Expr getCondition() {
        return condition;
    }

    public ForCondition getForCondition() {
        return forCondition;
    }

    public String getType() {
        return type;
    }

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
