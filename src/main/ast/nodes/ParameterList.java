package main.ast.nodes;
import main.visitor.IVisitor;
import java.util.ArrayList;

public class ParameterList extends Node{
    private ArrayList<ParameterDeclaration> parameters = new ArrayList<>();

    public ParameterList() {}

    public void addParameter(ParameterDeclaration parameter) {
        this.parameters.add(parameter);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
