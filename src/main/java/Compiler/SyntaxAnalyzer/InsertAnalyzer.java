package Compiler.SyntaxAnalyzer;

import Compiler.LexicalAnalyzer.Token;
import Compiler.TokenArrayList;

public class InsertAnalyzer {
    private TokenArrayList tokens;

    public InsertAnalyzer(TokenArrayList tokens) {
        this.tokens = tokens;
    }

    public boolean analyzeStatement() {
        if (tokens.getLast() != Token.RIGHT_PARENTHESIS) return false;
        int i = 2;
        if (tokens.get(2) == Token.INTO)  {
            if (tokens.get(1) != Token.WHITESPACE) return false;
            i = 4;
        }

        if (tokens.get(i - 1) != Token.WHITESPACE ||
                tokens.get(i++) != Token.CLIENTES)
            return false;

        if (tokens.get(i++) != Token.WHITESPACE ||
                tokens.get(i++) != Token.VALUES)
            return false;

        if (tokens.get(i) == Token.WHITESPACE) i++;
        if (tokens.get(i) != Token.LEFT_PARENTHESIS) return false;
        return analyzeAttributes(i + 1);
    }

    public boolean analyzeAttributes(int index) {
        Token[] desirableTokens = {Token.STRING, Token.STRING, Token.NUMBER, Token.NUMBER};
        tokens.add(tokens.size() - 1, Token.COMMA);
        for (Token token : desirableTokens) {
            if (tokens.get(index) == Token.WHITESPACE) index++;
            if (token == Token.NUMBER &&
                    tokens.get(index) != Token.NUMBER &&
                    tokens.get(index) != Token.INTEGER)
                return false;
            if (token != Token.NUMBER && tokens.get(index) != token) return false;
            if (tokens.get(++index) == Token.WHITESPACE) index++;
            if (tokens.get(index++) != Token.COMMA) return false;
        }
        return index == tokens.size() - 1;
    }
}
