package com.lab;

public class ParenthesisExpressionImpl implements ParenthesisExpression {

    private final Expression expression;

    ParenthesisExpressionImpl(Expression expr) {
        expression = expr;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) throws ExpressionParseException {
        return visitor.visitParenthesis(this);
    }

    @Override
    public Expression getExpr() {
        return expression;
    }
}
