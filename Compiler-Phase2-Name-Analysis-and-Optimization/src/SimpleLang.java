import main.ast.nodes.Expr.FunctionCallExpr;
import main.ast.nodes.ExternalDeclaration.FuncDefExtDec;
import main.ast.nodes.Program;
import main.grammar.SimpleLangLexer;
import main.grammar.SimpleLangParser;
import main.symbolTable.SymbolTable;
import main.visitor.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleLang {
    public static void main(String[] args) throws IOException {
        String sourceCode = new String(Files.readAllBytes(Paths.get(args[0])));
        String convertedCode = CodeFormatter.format(sourceCode);

        ConstValueChanger constValueChanger = new ConstValueChanger();
        String convertedCode1 = constValueChanger.transform(convertedCode);

        CharStream reader = CharStreams.fromString(convertedCode1);
        SimpleLangLexer simpleLangLexer = new SimpleLangLexer(reader);
        CommonTokenStream tokens = new CommonTokenStream(simpleLangLexer);
        SimpleLangParser flParser = new SimpleLangParser(tokens);
        Program program = flParser.program().programRet;
        System.out.println();

        int nameAnalError = 0;
        int round = 1;
        Boolean optimizeEnd = Boolean.FALSE;
        while (!optimizeEnd){
            //NameAnalyser
            NameAnalyser nameAnalyser = new NameAnalyser();
            nameAnalyser.visit(program);
            //ArrayList<String> declaredFunctions = nameAnalyser.getDeclaredFunctions();
            if(nameAnalyser.getError()) {
                nameAnalError = 1;
                break;
            }

            //getting Unused variables from the name analyser
            ArrayList<String> unusedVariables = nameAnalyser.getUnusedVariables();

            //removing unused variables from AST
            UnusedVariablesRemover unusedVariablesRemover = new UnusedVariablesRemover();
            unusedVariablesRemover.setUnusedVariables(unusedVariables);
            unusedVariablesRemover.visit(program);
            HashMap<String, List<Integer>> changedFunctions = new HashMap<>();
            changedFunctions = unusedVariablesRemover.getChangedFunctions();
            ArrayList<String> declaredFunctions = unusedVariablesRemover.getDeclaredFunctions();

            Boolean op = unusedVariablesRemover.getChange();

            //changing function calls if needed
            FunctionCallChanger functionCallsChanger = new FunctionCallChanger();
            functionCallsChanger.setChangedFunctions(changedFunctions);
            functionCallsChanger.visit(program);
            Boolean op1 = functionCallsChanger.getChange();

            //Delete After return statements
            AfterReturnRemover afterReturnRemover = new AfterReturnRemover();
            afterReturnRemover.visit(program);
            Boolean op3 = afterReturnRemover.getChange();

            //MainReachability checking
            FuncDefExtDec Main = nameAnalyser.getMain();
            int mainNumOfArgs = nameAnalyser.getMainNumOfArgs();
            MainReachabilityAnalyser mainReachabilityAnalyser = new MainReachabilityAnalyser();
            mainReachabilityAnalyser.checkMain(Main, "main-"+mainNumOfArgs);
            ArrayList<String> reachableFunctions = new ArrayList<>(mainReachabilityAnalyser.getReachableFunctions());

            while (true) {
                int reachNum = reachableFunctions.size();
                ArrayList<String> newReachableFunctions = new ArrayList<>(reachableFunctions); // Copy to iterate over

                for (String func : new ArrayList<>(reachableFunctions)) { // Iterate over a safe copy
                    mainReachabilityAnalyser.checkThisFunction(program.getTranslationUnit(), func);
                    newReachableFunctions = new ArrayList<>(mainReachabilityAnalyser.getReachableFunctions());
                }

                if (newReachableFunctions.size() == reachNum)
                    break;
                else
                    reachableFunctions = newReachableFunctions;
            }


            //remove unreachable functions
            List<String> unreachableFunctions = new ArrayList<>(declaredFunctions);
            unreachableFunctions.removeAll(reachableFunctions);

            UnrechabaleFunctionsRemover unrechabaleFunctionsRemover = new UnrechabaleFunctionsRemover();
            unrechabaleFunctionsRemover.setUnreachableFunctions(unreachableFunctions);
            unrechabaleFunctionsRemover.visit(program);
            Boolean op2 = unrechabaleFunctionsRemover.getChange();

            //Remove NonEffective statements
            NonEffectiveRemover nonEffectiveRemover = new NonEffectiveRemover();
            nonEffectiveRemover.visit(program);
            Boolean op4 = nonEffectiveRemover.getChange();

            optimizeEnd =!( op || op1 || op2 || op3 || op4);
            round++;
        }

        //Counting Statements and expressions
        if(nameAnalError != 1) {
            StmtCalculatorExpr stmtCalculatorExpr = new StmtCalculatorExpr();
            stmtCalculatorExpr.visit(program);
        }

        SymbolTable.root.hashCode();
        System.out.println();
    }
}