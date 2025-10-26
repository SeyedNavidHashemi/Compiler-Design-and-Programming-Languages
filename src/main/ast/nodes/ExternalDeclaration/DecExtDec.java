package main.ast.nodes.ExternalDeclaration;

import main.ast.nodes.DeclarationSpecifiers;
import main.ast.nodes.InitDeclaratorList;
import main.visitor.IVisitor;

public class DecExtDec extends ExternalDeclaration{
    private DeclarationSpecifiers declarationSpecifiers;
    private InitDeclaratorList initDeclaratorList;


    public DecExtDec(DeclarationSpecifiers declarationSpecifiers)
    {
        this.declarationSpecifiers = declarationSpecifiers;
    }


    public InitDeclaratorList getInitDeclaratorList() {
        return initDeclaratorList;
    }

    public void setInitDeclaratorList(InitDeclaratorList initDeclaratorList) {
        this.initDeclaratorList = initDeclaratorList;
    }

    public DeclarationSpecifiers getDeclarationSpecifiers() {
        return declarationSpecifiers;
    }

    public void setDeclarationSpecifiers(DeclarationSpecifiers declarationSpecifiers) {
        this.declarationSpecifiers = declarationSpecifiers;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
