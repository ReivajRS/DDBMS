package Compiler.LexicalAnalyzer;

public enum Token {
    SELECT, UPDATE, INSERT, DELETE,
    WHERE, FROM, NOT, IN, AND, OR, SET,
    INTO, EQUALS, NOT_EQUALS, LESS, VALUES,
    LESS_EQUALS, GREATER, GREATER_EQUALS,
    TIMES, PLUS, MINUS, DIVIDE, WHITESPACE,
    CLIENTES, IDCLIENTE, ESTADO, CREDITO, DEUDA, NOMBRE,
    ZONA, COMMA, LEFT_PARENTHESIS, RIGHT_PARENTHESIS,
    INTEGER, NUMBER, STRING, INVALID
}