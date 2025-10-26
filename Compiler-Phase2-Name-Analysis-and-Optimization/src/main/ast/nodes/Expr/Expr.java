package main.ast.nodes.Expr;

import main.ast.nodes.Node;

public abstract class Expr extends Node {
    private int depth = 0;
    public void addDepth(int num){this.depth += num;}
    public int getDepth(){return this.depth;}
    public void setDepth(int num){this.depth=0;}
    public int maxDepth(int left, int right){return Math.max(left, right);}
}
