package Compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class SyntaxAnalyzer {
    private HashMap<String, String> lexemesAndTokenName;

    public SyntaxAnalyzer() {
        lexemesAndTokenName = new HashMap<>();

        lexemesAndTokenName.put("SELECT", "select");
        lexemesAndTokenName.put("UPDATE", "update");
        lexemesAndTokenName.put("INSERT", "insert");
        lexemesAndTokenName.put("DELETE", "delete");
        lexemesAndTokenName.put("WHERE", "where");
        lexemesAndTokenName.put("IN", "in");
        lexemesAndTokenName.put("NOT", "not");
        lexemesAndTokenName.put("FROM", "from");
        lexemesAndTokenName.put("IS", "is");
        lexemesAndTokenName.put("NULL", "null");

        lexemesAndTokenName.put("(", "openpar");
        lexemesAndTokenName.put(")", "closepar");
        lexemesAndTokenName.put("'", "singlequotemark");
        lexemesAndTokenName.put("\"", "doublequotemark");
        lexemesAndTokenName.put("<", "lessthanop");
        lexemesAndTokenName.put("<=", "lesseqthanop");
        lexemesAndTokenName.put(">", "greaterthanop");
        lexemesAndTokenName.put(">=", "greatereqthanop");
        lexemesAndTokenName.put("==", "eqthanop");
        lexemesAndTokenName.put("!=", "noteqthanop");
    }

    public ArrayList<String> analyze(String lexeme) {
        ArrayList<String> tokens;
        return null;
    }
}
