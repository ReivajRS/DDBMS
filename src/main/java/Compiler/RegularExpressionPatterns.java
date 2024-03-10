package Compiler;

import java.util.regex.Pattern;

public class RegularExpressionPatterns {
    public static final Pattern NUMBER_PATTERN = Pattern.compile("[1-9][0-9]*(.[0-9]*)?"),
                                STRING_PATTERN = Pattern.compile("'[1-9a-zA-Z ]*'");
}
