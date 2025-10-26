package main.symbolTable.item;


import main.ast.nodes.ExternalDeclaration.FuncDefExtDec;

public class FuncDecSymbolTableItem extends SymbolTableItem {
    private FuncDefExtDec funcDec;
    private String funcName;
    private int numOfArgs;

    public static final String START_KEY = "FuncDec";

    public FuncDefExtDec getFuncDec() {
        return funcDec;
    }

    public void setFuncDec(FuncDefExtDec funcDec) {
        this.funcDec = funcDec;
    }

    public FuncDecSymbolTableItem(FuncDefExtDec funcDec, String funcName, int numOfArgs) {
        this.funcDec = funcDec;
        this.funcName = funcName;
        this.numOfArgs = numOfArgs;
    }

    @Override
    public String getKey() {
        return START_KEY + funcName+"-"+numOfArgs;
    }
}
