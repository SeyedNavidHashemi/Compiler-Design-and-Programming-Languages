package main.ast.nodes;
import main.visitor.IVisitor;
import java.util.ArrayList;

public class TypeName extends Node {
    private SpecifierQualifierList specifierQualifierList;
    private AbstractDeclarator abstractDeclarator;

    public TypeName(SpecifierQualifierList specifierQualifierList) {
        this.specifierQualifierList = specifierQualifierList;
    }
    public void setAbstractDeclarator(AbstractDeclarator abstractDeclarator) {
        this.abstractDeclarator = abstractDeclarator;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

