package main.ast.nodes.Expr;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class CommaExpr extends Expr{
    private ArrayList<Expr> expressions = new ArrayList<>();

    public CommaExpr(Expr expr) {this.expressions.add(expr);}
    public void addExpr(Expr expr){
        this.expressions.add(expr);
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {return visitor.visit(this);}

}