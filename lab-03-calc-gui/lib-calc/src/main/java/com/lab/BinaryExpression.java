package com.lab;

enum BinOpKind {
    SUB,
    ADD,
    MUL,
    DIV,
}

public interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
    BinOpKind getOperation();
}
