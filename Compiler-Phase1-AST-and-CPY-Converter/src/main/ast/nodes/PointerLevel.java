package main.ast.nodes;

public class PointerLevel {
    private boolean isConst;

    public PointerLevel(boolean isConst) {
        this.isConst = isConst;
    }
    public boolean isConst() {
        return isConst;
    }
}

