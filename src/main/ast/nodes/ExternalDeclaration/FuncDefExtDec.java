package main.ast.nodes.ExternalDeclaration;

import main.ast.nodes.DeclarationList;
import main.ast.nodes.DeclarationSpecifiers;
import main.ast.nodes.Declarator;
import main.ast.nodes.Stmt.CompoundStatement;
import main.visitor.IVisitor;

public class FuncDefExtDec extends ExternalDeclaration{

    private DeclarationSpecifiers declarationSpecifiers;
    private Declarator declarator;
    private DeclarationList declarationList;
    private CompoundStatement compoundStatement;

    public DeclarationSpecifiers getDeclarationSpecifiers() {
        return declarationSpecifiers;
    }
    public void setDeclarationSpecifiers(DeclarationSpecifiers declarationSpecifiers) {
        this.declarationSpecifiers = declarationSpecifiers;
    }

    public Declarator getDeclarator() {
        return declarator;
    }
    public void setDeclarator(Declarator declarator) {
        this.declarator = declarator;
    }


    public DeclarationList getDeclarationList() {
        return declarationList;
    }
    public void setDeclarationList(DeclarationList declarationList) {
        this.declarationList = declarationList;
    }


    public CompoundStatement getCompoundStatement() {
        return compoundStatement;
    }
    public void setCompoundStatement(CompoundStatement compoundStatement) {
        this.compoundStatement = compoundStatement;
    }
    public FuncDefExtDec() {}

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
