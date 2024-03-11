package Compiler.LexicalAnalyzer;

public class GeneratePutTokensInMap {
    public static void main(String[] args) {
        for (Token token : Token.values()) {
            if (token == Token.INTEGER) break;
            switch (token) {
                case Token.WHITESPACE:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\" \", Token." + token +");");
                    break;
                case Token.PLUS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"+\", Token." + token +");");
                    break;
                case Token.MINUS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"-\", Token." + token +");");
                    break;
                case Token.TIMES:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"*\", Token." + token +");");
                    break;
                case Token.DIVIDE:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"/\", Token." + token +");");
                    break;
                case Token.LEFT_PARENTHESIS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"(\", Token." + token +");");
                    break;
                case Token.RIGHT_PARENTHESIS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\")\", Token." + token +");");
                    break;
                case Token.COMMA:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\",\", Token." + token +");");
                    break;
                case Token.LESS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"<\", Token." + token +");");
                    break;
                case Token.LESS_EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"<=\", Token." + token +");");
                    break;
                case Token.GREATER:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\">\", Token." + token +");");
                    break;
                case Token.GREATER_EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\">=\", Token." + token +");");
                    break;
                case Token.EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"=\", Token." + token +");");
                    break;
                case Token.NOT_EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"!=\", Token." + token +");");
                    break;
                case Token.PLUS_EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"+=\", Token." + token +");");
                    break;
                case Token.SUB_EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"-=\", Token." + token +");");
                    break;
                case Token.MUL_EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"*=\", Token." + token +");");
                    break;
                case Token.DIV_EQUALS:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"/=\", Token." + token +");");
                    break;
                default:
                    System.out.println("\t\t\tkeywordsAndTokens.put(\"" + token + "\", Token." + token +");");
            }

        }
    }
}
