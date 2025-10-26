package main.ast.nodes;
import main.ast.nodes.ExternalDeclaration.ExternalDeclaration;
import main.visitor.IVisitor;
import java.util.ArrayList;

public class TranslationUnit extends Node{

    private ArrayList<ExternalDeclaration> externalDeclarations = new ArrayList<>();

    public TranslationUnit() {}
    public void addExternalDeclaration(ExternalDeclaration externalDeclaration) {
        this.externalDeclarations.add(externalDeclaration);
    }

    public ArrayList<ExternalDeclaration> getExternalDeclarations() {
        return externalDeclarations;
    }


    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
