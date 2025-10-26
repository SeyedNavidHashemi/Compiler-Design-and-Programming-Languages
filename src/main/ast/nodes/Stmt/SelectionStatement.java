package main.ast.nodes.Stmt;
import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class SelectionStatement extends Stmt {
    private Expr condition;
    private Stmt thenStmt;
    private ArrayList<Expr> elseIfConditions = new ArrayList<>();
    private ArrayList<Stmt> elseIfThenStmts = new ArrayList<>();
    private Stmt elseStmt;
    private int elseLine;

    public SelectionStatement() {}

    public void addElseIfConditions(Expr condition) {this.elseIfConditions.add(condition);}
    public void addElseIfThenStmts(Stmt thenStmt) {this.elseIfThenStmts.add(thenStmt);}

    public void setElseLine(int line){this.elseLine = line;}
    public int getElseLine(){return this.elseLine;}
    public ArrayList<Expr> getElseIfConditions() {
        return elseIfConditions;
    }

    public void setElseIfConditions(ArrayList<Expr> elseIfConditions) {
        this.elseIfConditions = elseIfConditions;
    }

    public Stmt getThenStmt() {
        return thenStmt;
    }

    public void setThenStmt(Stmt thenStmt) {
        this.thenStmt = thenStmt;
    }

    public Expr getCondition() {
        return condition;
    }

    public void setCondition(Expr condition) {
        this.condition = condition;
    }

    public ArrayList<Stmt> getElseIfThenStmts() {
        return elseIfThenStmts;
    }

    public void setElseIfThenStmts(ArrayList<Stmt> elseIfThenStmts) {
        this.elseIfThenStmts = elseIfThenStmts;
    }

    public Stmt getElseStmt() {
        return elseStmt;
    }

    public void setElseStmt(Stmt elseStmt) {
        this.elseStmt = elseStmt;
    }

    public int countNumStmt(){
        int numStmt = 0;
        if(elseStmt != null)
            numStmt += 1;
        if(thenStmt != null)
            numStmt += 1;
        numStmt += elseIfConditions.size();
        return numStmt;
    }

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
