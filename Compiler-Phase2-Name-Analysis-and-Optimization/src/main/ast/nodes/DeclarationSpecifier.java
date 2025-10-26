package main.ast.nodes;

import main.visitor.IVisitor;

public class DeclarationSpecifier extends Node {
    private String name;

    public String getName() {
        return name;
    }
    public DeclarationSpecifier(String name) {this.name = name;}

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
