package Compiler.SyntaxAnalyzer;

import Compiler.LexicalAnalyzer.Token;
import Compiler.TokenArrayList;

import java.util.HashSet;

public class SelectAnalyzer {
    private TokenArrayList tokens;

    public SelectAnalyzer(TokenArrayList tokens) {
        this.tokens = tokens;
    }

    public boolean analyzeStatement() {
        if (tokens.get(1) != Token.WHITESPACE ||
            !tokens.contains(Token.FROM) ||
            !analyzeAttributes(tokens.indexOf(Token.FROM)))
            return false;

        int i = tokens.indexOf(Token.FROM);
        if (tokens.get(++i) != Token.WHITESPACE ||
            tokens.get(++i) != Token.CLIENTES)
            return false;

        if (i == tokens.size() - 1) return true;

        if (tokens.get(++i) != Token.WHITESPACE ||
                tokens.get(++i) != Token.WHERE ||
                tokens.get(++i) != Token.WHITESPACE)
            return false;

        while (i-- != 0)
            tokens.removeFirst();
        return new WhereAnalyzer(tokens).analyzeStatement();
    }

    public boolean analyzeAttributes(int indexOfTokenFrom) {
        if (tokens.get(indexOfTokenFrom - 1) != Token.WHITESPACE)
            return false;

        if (indexOfTokenFrom == 4 &&
            tokens.get(2) == Token.TIMES &&
            tokens.get(3) == Token.WHITESPACE)
            return true;

        if (indexOfTokenFrom == 4 && tokens.get(2) == Token.TIMES) return true;

        tokens.add(indexOfTokenFrom++, Token.COMMA);

        HashSet<Token> hs = new HashSet<>();
        for (int i = 2; i < indexOfTokenFrom; i++) {
            if (tokens.get(i) != Token.ZONA &&
                tokens.get(i) != Token.ESTADO &&
                tokens.get(i) != Token.IDCLIENTE &&
                tokens.get(i) != Token.NOMBRE &&
                tokens.get(i) != Token.CREDITO &&
                tokens.get(i) != Token.DEUDA)
                return false;
            if (hs.contains(tokens.get(i))) return false;
            hs.add(tokens.get(i++));
            if (tokens.get(i) == Token.WHITESPACE) i++;
            if (tokens.get(i++) != Token.COMMA) return false;
        }
        return true;
    }
}
