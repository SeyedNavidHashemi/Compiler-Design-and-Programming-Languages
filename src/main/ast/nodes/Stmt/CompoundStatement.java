package main.ast.nodes.Stmt;
import main.ast.nodes.BlockItem;
import main.ast.nodes.Node;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class CompoundStatement extends Stmt {

    private ArrayList<BlockItem> blocks = new ArrayList<>();

    public CompoundStatement() {}

    public ArrayList<BlockItem> getBlocks() {
        return blocks;
    }

    public void addBlockItem(BlockItem item) {
        this.blocks.add(item);
    }

    public int getNumStmt(){return blocks.size();}

    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
