package main.ast.nodes;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class InitializerList extends Node {

    private ArrayList<DesignationInitializerTuple> initializers = new ArrayList<>();

    public InitializerList(){}

    public void addInitializer(DesignationInitializerTuple initializer){this.initializers.add(initializer);}

    public ArrayList<Designation> getDesignationList(){
        ArrayList<Designation> designations = new ArrayList<>();
        for(DesignationInitializerTuple initializerTuple : initializers){
            if(initializerTuple.getDesignation() != null){
                designations.add(initializerTuple.getDesignation());
            }
        }
        return designations;
    }
    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

