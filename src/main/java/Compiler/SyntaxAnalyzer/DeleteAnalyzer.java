package Compiler.SyntaxAnalyzer;

import Compiler.LexicalAnalyzer.Token;
import Compiler.TokenArrayList;

public class DeleteAnalyzer {
    private TokenArrayList tokens;
    public DeleteAnalyzer(TokenArrayList tokens) {
        this.tokens = tokens;
    }
    public boolean analyzeStatement() {
        if (tokens.get(1) != Token.WHITESPACE ||
                tokens.get(2) != Token.FROM ||
                tokens.get(3) != Token.WHITESPACE ||
                tokens.get(4) != Token.CLIENTES)
            return false;

        if (tokens.size() == 5) return true;

        if (tokens.get(5) == Token.WHITESPACE ||
            tokens.get(6) != Token.WHERE ||
            tokens.get(7) != Token.WHITESPACE)
            return false;

        for (int i = 0; i < 8; i++) tokens.removeFirst();

        return new WhereAnalyzer(tokens).analyzeStatement();
    }
}
