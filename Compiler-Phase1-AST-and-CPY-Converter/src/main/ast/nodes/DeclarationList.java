package main.ast.nodes;

import main.ast.nodes.ExternalDeclaration.DecExtDec;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class DeclarationList extends Node {

    private ArrayList<DecExtDec> declarations = new ArrayList<>();
    public DeclarationList() {}

    public void addDeclaration(DecExtDec declaration) {
        this.declarations.add(declaration);
    }
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
