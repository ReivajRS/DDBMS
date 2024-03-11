package Compiler.LexicalAnalyzer;

public class GeneratePutTokensInMap {
    public static void main(String[] args) {
        for (Token token : Token.values()) {
            if (token == Token.INTEGER) break;
            if (token == Token.WHITESPACE) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\" \", Token." + token +");");
                continue;
            }
            if (token == Token.PLUS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"+\", Token." + token +");");
                continue;
            }
            if (token == Token.MINUS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"-\", Token." + token +");");
                continue;
            }
            if (token == Token.TIMES) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"*\", Token." + token +");");
                continue;
            }
            if (token == Token.DIVIDE) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"/\", Token." + token +");");
                continue;
            }
            if (token == Token.LEFT_PARENTHESIS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"(\", Token." + token +");");
                continue;
            }
            if (token == Token.RIGHT_PARENTHESIS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\")\", Token." + token +");");
                continue;
            }
            if (token == Token.COMMA) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\",\", Token." + token +");");
                continue;
            }
            if (token == Token.LESS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"<\", Token." + token +");");
                continue;
            }
            if (token == Token.LESS_EQUALS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"<=\", Token." + token +");");
                continue;
            }
            if (token == Token.GREATER) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\">\", Token." + token +");");
                continue;
            }
            if (token == Token.GREATER_EQUALS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\">=\", Token." + token +");");
                continue;
            }
            if (token == Token.EQUALS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"=\", Token." + token +");");
                continue;
            }
            if (token == Token.NOT_EQUALS) {
                System.out.println("\t\t\tkeywordsAndTokens.put(\"!=\", Token." + token +");");
                continue;
            }
            System.out.println("\t\t\tkeywordsAndTokens.put(\"" + token + "\", Token." + token +");");
        }
    }
}
