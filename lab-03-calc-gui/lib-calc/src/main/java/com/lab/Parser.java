package com.lab;

public interface Parser {
    Expression parseExpression(String input) throws ExpressionParseException;
}

