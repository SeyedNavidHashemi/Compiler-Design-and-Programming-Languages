package main.ast.nodes;
import main.visitor.IVisitor;
import java.util.ArrayList;

public class SpecifierQualifierList extends Node {
    private ArrayList<String> specifiers = new ArrayList<>();;

    public SpecifierQualifierList() {}

    // Add a specifier to the list
    public void addSpecifier(String specifier) {
        this.specifiers.add(specifier);
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

