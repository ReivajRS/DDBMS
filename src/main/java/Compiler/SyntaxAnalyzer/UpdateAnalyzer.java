package Compiler.SyntaxAnalyzer;

import Compiler.TokenArrayList;
import Compiler.LexicalAnalyzer.Token;

import java.util.Arrays;
import java.util.List;

public class UpdateAnalyzer {
    private final TokenArrayList tokens;

    public UpdateAnalyzer(TokenArrayList tokens) {
        this.tokens = tokens;
    }

    public boolean analyzeStatement() {
        if (tokens.get(1) != Token.WHITESPACE ||
            tokens.get(2) != Token.CLIENTES ||
            tokens.get(3) != Token.WHITESPACE ||
            tokens.get(4) != Token.SET ||
            tokens.get(5) != Token.WHITESPACE)
            return false;

        if (!analyzeSet()) return false;

        int indexOfWhereKeyword = tokens.indexOf(Token.WHERE);
        if (indexOfWhereKeyword == -1) return true;
        for (int i = 0; i < indexOfWhereKeyword; i++) tokens.removeFirst();
        return new WhereAnalyzer(tokens).analyzeStatement();
    }

    public boolean analyzeSet() {
        int indexOfWhereKeyword = tokens.indexOf(Token.WHERE);
        if (indexOfWhereKeyword == -1) indexOfWhereKeyword = tokens.size();
        tokens.add(indexOfWhereKeyword++, Token.COMMA);
        List<Token> attributes = Arrays.asList(Token.NOMBRE, Token.ESTADO, Token.CREDITO, Token.DEUDA);
        List<Token> assignment = Arrays.asList(Token.EQUALS, Token.PLUS_EQUALS, Token.SUB_EQUALS, Token.MUL_EQUALS, Token.DIV_EQUALS);
        List<Token> operators = Arrays.asList(Token.PLUS, Token.MINUS, Token.TIMES, Token.DIVIDE);
        int i = 6;
        while (i < indexOfWhereKeyword) {
            if (!attributes.contains(tokens.get(i++)))
                return false;
            if (tokens.get(i) == Token.WHITESPACE) i++;
            if (!assignment.contains(tokens.get(i++))) return false;
            if (tokens.get(i) == Token.WHITESPACE) i++;
            boolean operatorWaiting = true;
            while (tokens.get(i) != Token.COMMA) {
                if (tokens.get(i) == Token.WHITESPACE) {
                    i++;
                    continue;
                }
                if (attributes.contains(tokens.get(i)) ||
                        tokens.get(i) == Token.NUMBER ||
                        tokens.get(i) == Token.INTEGER) {
                    if (!operatorWaiting) return false;
                    operatorWaiting = false;
                    i++;
                    continue;
                }
                if (operators.contains(tokens.get(i))) {
                    if (operatorWaiting) return false;
                    operatorWaiting = true;
                    i++;
                    continue;
                }
                return false;
            }
            if (operatorWaiting) return false;
            i++;
        }
        return true;
    }
}