package com.lab;

public class VariableImpl implements Variable {

    private final String variable;

    VariableImpl(String string) {
        variable = string;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) throws ExpressionParseException {
        return visitor.visitVariable(this);
    }

    @Override
    public String getVariable() {
        return variable;
    }
}
