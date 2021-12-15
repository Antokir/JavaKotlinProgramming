package com.lab;

public interface Expression {
    Object accept(ExpressionVisitor visitor) throws ExpressionParseException;
}
