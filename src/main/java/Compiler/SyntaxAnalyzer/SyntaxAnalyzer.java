package Compiler.SyntaxAnalyzer;

import Compiler.LexicalAnalyzer.LexicalAnalyzer;
import Compiler.TokenArrayList;

public class SyntaxAnalyzer {
    public static boolean analyzeStatement(String statement) {
        TokenArrayList tokens = new LexicalAnalyzer(statement).getAllTokens();

        return switch (tokens.getFirst()) {
            case SELECT -> new SelectAnalyzer(tokens).analyzeStatement();
            case INSERT -> new InsertAnalyzer(tokens).analyzeStatement();
            case UPDATE -> new UpdateAnalyzer(tokens).analyzeStatement();
            case DELETE -> new DeleteAnalyzer(tokens).analyzeStatement();
            default -> true;
        };
    }
}