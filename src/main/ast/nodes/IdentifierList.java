package main.ast.nodes;
import main.ast.nodes.Expr.Identifier;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class IdentifierList extends Node {
    private ArrayList<Identifier> identifiers = new ArrayList<>();

    public IdentifierList(Identifier id) {this.identifiers.add(id);}

    public void addIdentifier(Identifier id) {
        this.identifiers.add(id);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
