package main.ast.nodes;
import main.visitor.IVisitor;

public class InitDeclarator extends Node {
    private Declarator declarator;
    private Initializer initializer;

    public InitDeclarator(Declarator declarator) {this.declarator = declarator;}

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    public Declarator getDeclarator() {
        return declarator;
    }

    public Initializer getInitializer() {
        return initializer;
    }




    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

