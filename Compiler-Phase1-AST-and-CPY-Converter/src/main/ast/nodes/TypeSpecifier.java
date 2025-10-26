package main.ast.nodes;
import main.visitor.IVisitor;

public class TypeSpecifier extends Node {
    private String type;

    public TypeSpecifier(String type, int line) {
        this.type = type;
        this.setLine(line);
    }

    public String getType() {
        return type;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

