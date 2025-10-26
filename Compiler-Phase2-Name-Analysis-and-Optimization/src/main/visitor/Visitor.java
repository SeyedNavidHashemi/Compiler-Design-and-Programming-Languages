package main.visitor;

import main.ast.nodes.ExternalDeclaration.DecExtDec;
import main.ast.nodes.ExternalDeclaration.FuncDefExtDec;
import main.ast.nodes.Program;
import main.ast.nodes.TranslationUnit;

/*GOALs:
*   1. print out scope changes each time a new scope starts
*   2. print the identifier if it is initialized
*   3. print the identifier if it is used
*   4. print out the name of the function when it is defined
*   5. print out the name of the function when it is used
*
* */

public abstract class Visitor<T> implements IVisitor<T> {
    @Override
    public T visit(Program program) {
        return null;
    }
    public T visit(TranslationUnit translationUnit) {return null; }
    public T visit(DecExtDec DecExtDec) {return null; }
    public T visit(FuncDefExtDec funcDefExtDec) {return null;}
}
