package main.ast.nodes;

import main.ast.nodes.ExternalDeclaration.DecExtDec;
import main.ast.nodes.Stmt.Stmt;
import main.visitor.IVisitor;

public class AbstractDeclarator extends Node{
    Pointer pointer;

    public DirectAbstractDeclarator getDirectAbstractDeclarator() {
        return directAbstractDeclarator;
    }

    DirectAbstractDeclarator directAbstractDeclarator;

    public AbstractDeclarator() {}


    public void setPointer(Pointer pointer) {
            this.pointer = pointer;
    }

    public void setDirectAbstractDeclarator(DirectAbstractDeclarator directAbstractDeclarator) {
        this.directAbstractDeclarator = directAbstractDeclarator;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
