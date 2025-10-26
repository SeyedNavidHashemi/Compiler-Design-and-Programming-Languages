package main.ast.nodes.DirectDeclarator;
import main.ast.nodes.IdentifierList;
import main.ast.nodes.ParameterList;
import main.visitor.IVisitor;

public class FunctionDeclarator extends DirectDeclarator {
    private DirectDeclarator baseDirectDeclarator;
    private ParameterList parameterList;
    private IdentifierList identifierList;
    private boolean isOldStyle;

    public FunctionDeclarator(DirectDeclarator baseDirectDeclarator) {this.baseDirectDeclarator = baseDirectDeclarator;}

    public DirectDeclarator getBaseDirectDeclarator() {
        return baseDirectDeclarator;
    }

    public void setIsOldStyle(boolean oldStyle) {
        isOldStyle = oldStyle;
    }
    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }
    public void setIdentifierList(IdentifierList identifierList) {
        this.identifierList = identifierList;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}