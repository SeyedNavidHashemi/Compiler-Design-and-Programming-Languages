package main.ast.nodes;

import main.visitor.IVisitor;

public class DeclarationSpecifier extends Node {
    private String type;

    public DeclarationSpecifier(String type) {this.type = type;}

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
