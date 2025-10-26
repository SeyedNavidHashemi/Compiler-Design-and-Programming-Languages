package main.symbolTable.item;


public class VarDecSymbolTableItem extends SymbolTableItem{
    public static final String START_KEY = "VarDec_";
    private String varType;
    private String varName;
    private int used;
    private int line;


    public void setUsed(int used) {
        this.used = used;
    }

    public int getUsed() {
        return used;
    }

    public int getLine() {
        return line;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public VarDecSymbolTableItem(String varType, String varName, int line) {
        this.varType = varType;
        this.varName = varName;
        this.used = 0;
        this.line = line;
    }

    @Override
    public String getKey() {
        return START_KEY + this.varName;
    }

}
