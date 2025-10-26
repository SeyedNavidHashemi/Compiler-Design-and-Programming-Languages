package main.ast.nodes;

import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

public class DirectAbstractDeclarator extends Node{
    public enum Type {
        ARRAY,
        FUNCTION,
        NESTED
    }
    private int type;
    private Expr expr;
    private DirectAbstractDeclarator next;
    private AbstractDeclarator abstractDeclarator;
    private ParameterList parameterList;

    public DirectAbstractDeclarator() {}

    public void setType(int type) {
        this.type = type;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public void setNext(DirectAbstractDeclarator next) {
        this.next = next;
    }

    public void setAbstractDeclarator(AbstractDeclarator abstractDeclarator) {
        this.abstractDeclarator = abstractDeclarator;
    }

    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
