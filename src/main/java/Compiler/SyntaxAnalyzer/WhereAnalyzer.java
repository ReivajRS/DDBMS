package Compiler.SyntaxAnalyzer;

import Compiler.TokenArrayList;
import Compiler.LexicalAnalyzer.Token;
import java.util.List;
import java.util.Arrays;

public class WhereAnalyzer {
    private final List<Token> STRING_ATTRIBUTES = Arrays.asList(Token.ZONA, Token.NOMBRE, Token.ESTADO);
    private final List<Token> INTEGER_ATTRIBUTES = Arrays.asList(Token.CREDITO, Token.DEUDA);
    private final List<Token> CONNECTORS = Arrays.asList(Token.AND, Token.OR);
    private final List<Token> OPERATORS = Arrays.asList(Token.EQUALS, Token.NOT_EQUALS, Token.LESS, Token.LESS_EQUALS,
                                                            Token.GREATER, Token.GREATER_EQUALS);
    private final TokenArrayList tokens;


    public WhereAnalyzer(TokenArrayList tokens) {
        this.tokens = tokens;
    }

    public boolean analyzeStatement() {
        if (tokens.getFirst() == Token.WHERE) tokens.removeFirst();
        int i = 0;
        boolean isTheFirstCondition = true;
        while (i < tokens.size()) {
            if (tokens.get(i) == Token.WHITESPACE) {
                i++;
                continue;
            }
            if (!isTheFirstCondition) {
                if (!CONNECTORS.contains(tokens.get(i++))) return false;
                i++;
                if (tokens.get(i++) != Token.WHITESPACE) return false;
            }
            isTheFirstCondition = false;
            boolean isStringAttribute = false;
            if (STRING_ATTRIBUTES.contains(tokens.get(i))) isStringAttribute = true;
            else if (!INTEGER_ATTRIBUTES.contains(tokens.get(i))) return false;
            i++;
            if (tokens.get(i) == Token.WHITESPACE) i++;
            if (tokens.get(i) == Token.NOT) {
                if (tokens.get(++i) != Token.WHITESPACE) return false;
                i++;
            }
            if (tokens.get(i) == Token.IN) {
                if (tokens.get(++i) != Token.WHITESPACE ||
                    tokens.get(++i) != Token.LEFT_PARENTHESIS)
                    return false;
                while (tokens.get(i) != Token.RIGHT_PARENTHESIS &&
                        tokens.get(i) != Token.INVALID) {
                    if (tokens.get(i) == Token.WHITESPACE) i++;
                    if ((isStringAttribute && tokens.get(i) != Token.STRING) ||
                        (!isStringAttribute && tokens.get(i) != Token.INTEGER && tokens.get(i) != Token.NUMBER))
                        return false;
                    i++;
                    if (tokens.get(i) == Token.WHITESPACE) i++;
                    if (tokens.get(i) == Token.COMMA) i++;
                }
                if (tokens.get(i) == Token.INVALID) return false;
                continue;
            }
            if (OPERATORS.contains(tokens.get(i++))) {
                if (tokens.get(i++) != Token.WHITESPACE) return false;
                if ((isStringAttribute && tokens.get(i) != Token.STRING) ||
                    (!isStringAttribute && tokens.get(i) != Token.INTEGER && tokens.get(i) != Token.NUMBER))
                    return false;
                i++;
                continue;
            }
            return false;
        }
        return true;
    }
}