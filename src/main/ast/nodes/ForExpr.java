package main.ast.nodes;
import main.ast.nodes.Expr.Expr;
import main.ast.nodes.ExternalDeclaration.DecExtDec;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class ForExpr extends Node {

    public ArrayList<Expr> getExpressions() {
        return expressions;
    }

    private ArrayList<Expr> expressions = new ArrayList<>();

    public ForExpr(Expr expr) {this.expressions.add(expr);}

    public void addExpr(Expr expr) {this.expressions.add(expr);}
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

