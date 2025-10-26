package main.ast.nodes;

import main.ast.nodes.DirectDeclarator.DirectDeclarator;
import main.visitor.IVisitor;

public class Declarator extends Node {

    private Pointer pointer;

    public DirectDeclarator getDirectDeclarator() {
        return directDeclarator;
    }

    private DirectDeclarator directDeclarator;

    public Declarator() {}

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    public void setDirectDeclarator(DirectDeclarator directDeclarator) {
        this.directDeclarator = directDeclarator;
    }

    public <T> T accept(IVisitor<T> visitor) {
        return (T) visitor.visit(this);
    }
}
