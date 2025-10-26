package main.ast.nodes;

import main.visitor.IVisitor;
import java.util.ArrayList;

public class DeclarationSpecifiers extends Node {
    private ArrayList<DeclarationSpecifier> declarationSpecifiers = new ArrayList<>();


    public DeclarationSpecifiers() {}
    public void addDeclarationSpecifier(DeclarationSpecifier dec){
        this.declarationSpecifiers.add(dec);
    }

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
