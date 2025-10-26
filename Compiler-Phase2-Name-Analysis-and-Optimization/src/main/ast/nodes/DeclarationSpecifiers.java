package main.ast.nodes;

import main.symbolTable.item.VarDecSymbolTableItem;
import main.visitor.IVisitor;
import java.util.ArrayList;

public class DeclarationSpecifiers extends Node {
    public ArrayList<DeclarationSpecifier> getDeclarationSpecifiers() {
        return declarationSpecifiers;
    }

    private ArrayList<DeclarationSpecifier> declarationSpecifiers = new ArrayList<>();
//    private VarDecSymbolTableItem varDecSymbolTableItem= new VarDecSymbolTableItem();

    public DeclarationSpecifiers() {}
    public void addDeclarationSpecifier(DeclarationSpecifier dec){
        this.declarationSpecifiers.add(dec);
    }
//
//    public void setVarDecItem()
//    {
//        varDecSymbolTableItem.setVarType(this.declarationSpecifiers.get(0).getName());
//        varDecSymbolTableItem.setVarName(this.declarationSpecifiers.get(1).getName());
//    }

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
