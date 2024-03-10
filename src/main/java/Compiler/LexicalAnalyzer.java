package Compiler;

import java.util.HashMap;

public class LexicalAnalyzer {
    private final HashMap<String, Token> keywordsAndTokens;
    private String inputText;

    public LexicalAnalyzer(String inputText) {
        this.inputText = inputText;
        keywordsAndTokens = new HashMap<>();
        keywordsAndTokens.put("SELECT", Token.SELECT);
        keywordsAndTokens.put("UPDATE", Token.UPDATE);
        keywordsAndTokens.put("INSERT", Token.INSERT);
        keywordsAndTokens.put("DELETE", Token.DELETE);
        keywordsAndTokens.put("WHERE", Token.WHERE);
        keywordsAndTokens.put("VALUES", Token.VALUES);
        keywordsAndTokens.put("FROM", Token.FROM);
        keywordsAndTokens.put("NOT", Token.NOT);
        keywordsAndTokens.put("IN", Token.IN);
        keywordsAndTokens.put("AND", Token.AND);
        keywordsAndTokens.put("OR", Token.OR);
        keywordsAndTokens.put("SET", Token.SET);
        keywordsAndTokens.put("=", Token.EQUALS);
        keywordsAndTokens.put("!=", Token.NOT_EQUALS);
        keywordsAndTokens.put("<", Token.LESS);
        keywordsAndTokens.put("<=", Token.LESS_EQUALS);
        keywordsAndTokens.put(">", Token.GREATER);
        keywordsAndTokens.put(">=", Token.GREATER_EQUALS);
        keywordsAndTokens.put("*", Token.TIMES);
        keywordsAndTokens.put("+", Token.PLUS);
        keywordsAndTokens.put("-", Token.MINUS);
        keywordsAndTokens.put("/", Token.DIVIDE);
        keywordsAndTokens.put(" ", Token.WHITESPACE);
        keywordsAndTokens.put("CLIENTE", Token.CLIENTE);
        keywordsAndTokens.put("ESTADO", Token.ESTADO);
        keywordsAndTokens.put("CREDITO", Token.CREDITO);
        keywordsAndTokens.put("DEUDA", Token.DEUDA);
        keywordsAndTokens.put("NOMBRE", Token.NOMBRE);
        keywordsAndTokens.put("ZONA", Token.ZONA);
        keywordsAndTokens.put(",", Token.COMMA);
        keywordsAndTokens.put("(", Token.LEFT_PARENTHESIS);
        keywordsAndTokens.put(")", Token.RIGHT_PARENTHESIS);
    }

    public boolean tokensAvailable() {
        return !inputText.isEmpty();
    }

    public Token getNextToken() {
        int lengthOfLargestTokenFound = 0;
        for (int prefixLength = 1; prefixLength <= inputText.length(); prefixLength++)
            if (getTokenInPrefix(prefixLength) != Token.INVALID)
                lengthOfLargestTokenFound = prefixLength;
        inputText = inputText.substring(lengthOfLargestTokenFound);
        return getTokenInPrefix(lengthOfLargestTokenFound);
    }

    private Token getTokenInPrefix(int prefixLength) {
        if (prefixLength == 0) return Token.INVALID;
        String prefix = inputText.substring(0, prefixLength);
        if (keywordsAndTokens.containsKey(prefix))
            return keywordsAndTokens.get(prefix);
        if (RegularExpressionPatterns.NUMBER_PATTERN.matcher(prefix).matches())
            return Token.NUMBER;
        if (RegularExpressionPatterns.STRING_PATTERN.matcher(prefix).matches())
            return Token.STRING;
        return Token.INVALID;
    }
}