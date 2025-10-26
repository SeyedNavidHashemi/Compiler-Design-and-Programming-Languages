package main.ast.nodes;

import main.visitor.IVisitor;

public class ParameterDeclaration extends Node{
    private DeclarationSpecifiers specifiers;
    private Declarator declarator;
    private AbstractDeclarator abstractDeclarator;

    public ParameterDeclaration(DeclarationSpecifiers specifiers) {
        this.specifiers = specifiers;
    }

    public void setDeclarator(Declarator declarator) {
        this.declarator = declarator;
    }

    public void setAbstractDeclarator(AbstractDeclarator abstractDeclarator) {
        this.abstractDeclarator = abstractDeclarator;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
