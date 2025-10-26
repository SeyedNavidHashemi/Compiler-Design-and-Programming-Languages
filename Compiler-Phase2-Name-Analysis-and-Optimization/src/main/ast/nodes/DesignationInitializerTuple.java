package main.ast.nodes;
import main.visitor.IVisitor;

public class DesignationInitializerTuple extends Node {

    private Designation designation;
    private Initializer initializer;

    public DesignationInitializerTuple() {}

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    public Designation getDesignation(){return this.designation;}
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

