package Compiler;

import Compiler.LexicalAnalyzer.Token;

import java.util.ArrayList;

public class TokenArrayList extends ArrayList<Token> {
    public TokenArrayList() {
        super();
    }

    @Override
    public Token get(int index) {
        if (index >= size()) return Token.INVALID;
        return super.get(index);
    }
}