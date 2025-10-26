package main.ast.nodes.DirectDeclarator;
import main.ast.nodes.Declarator;
import main.visitor.IVisitor;

public class NestedDeclarator extends DirectDeclarator {
    Declarator innerDeclarator;

    public NestedDeclarator(Declarator _innerDeclarator) {this.innerDeclarator = _innerDeclarator;}
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}