package main.ast.nodes;
import main.ast.nodes.Expr.Expr;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class InitDeclaratorList extends Node {
    private ArrayList<InitDeclarator> initDeclarators = new ArrayList<>();

    public InitDeclaratorList(InitDeclarator initDec) {this.initDeclarators.add(initDec);}


    public ArrayList<InitDeclarator> getInitDeclarators() {
        return initDeclarators;
    }


    public void addInitDeclarator(InitDeclarator initDec) {this.initDeclarators.add(initDec);}
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

