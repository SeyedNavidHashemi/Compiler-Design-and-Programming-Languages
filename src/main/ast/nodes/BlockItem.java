package main.ast.nodes;
import main.ast.nodes.ExternalDeclaration.DecExtDec;
import main.ast.nodes.Stmt.Stmt;
import main.visitor.IVisitor;

public class BlockItem extends Node{
    private DecExtDec declaration;
    private Stmt statement;

    public BlockItem() {}

    public void setDeclaration(DecExtDec declaration) {
        this.declaration = declaration;
    }

    public void setStatement(Stmt statement) {
        this.statement = statement;
    }

    public Stmt getStatement() {
        return statement;
    }

    public DecExtDec getDeclaration() {
        return declaration;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return null;
    }
}
