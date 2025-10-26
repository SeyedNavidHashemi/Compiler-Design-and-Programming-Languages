import main.ast.nodes.Program;
import main.grammar.SimpleLangLexer;
import main.grammar.SimpleLangParser;
import main.visitor.StmtCalculatorExpr;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleLang {
    public static void main(String[] args) throws IOException {
        String sourceCode = new String(Files.readAllBytes(Paths.get(args[0])));
        String convertedCode = CodeFormatter.format(sourceCode);

        CharStream reader = CharStreams.fromString(convertedCode);
        SimpleLangLexer simpleLangLexer = new SimpleLangLexer(reader);
        CommonTokenStream tokens = new CommonTokenStream(simpleLangLexer);
        SimpleLangParser flParser = new SimpleLangParser(tokens);
        Program program = flParser.program().programRet;
        System.out.println();

        StmtCalculatorExpr stmtCalculator = new StmtCalculatorExpr();
        stmtCalculator.visit(program);
        //final scheck
    }
}