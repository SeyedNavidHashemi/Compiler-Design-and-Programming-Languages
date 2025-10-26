package main.ast.nodes;
import main.visitor.IVisitor;
import java.util.ArrayList;

public class Pointer extends Node {
    private ArrayList<PointerLevel> pointerLevels = new ArrayList<>();

    public void addPointerLevel(PointerLevel level) {
        pointerLevels.add(level);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

