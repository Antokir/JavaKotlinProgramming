package com.lab;

public class LiteralImpl implements Literal {

    private final double value;

    LiteralImpl(double val) {
        value = val;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }
}
