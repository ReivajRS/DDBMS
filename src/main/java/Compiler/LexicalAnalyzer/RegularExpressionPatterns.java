package Compiler.LexicalAnalyzer;

import java.util.regex.Pattern;

public class RegularExpressionPatterns {
    public static final Pattern INTEGER_PATTERN = Pattern.compile("[0(1-90-9*)]"),
            NUMBER_PATTERN = Pattern.compile("[0(1-90-9*.0-9+)]"),
            STRING_PATTERN = Pattern.compile("'[0-9a-zA-Z %]*'");
}
