package main.ast.nodes.DirectDeclarator;
import main.visitor.IVisitor;

public class IdentifierDeclarator extends DirectDeclarator {
    String name;

    public IdentifierDeclarator(String _name)
    {
        this.name = _name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return (T) visitor.visit(this);
    }
}