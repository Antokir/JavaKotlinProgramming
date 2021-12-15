package com.lab;

public class BinaryExpressionImpl implements BinaryExpression {

    private final BinOpKind operation;
    private final Expression left;
    private final Expression right;

    BinaryExpressionImpl(Expression l_expr, Expression r_expr, BinOpKind opKind) {
        left = l_expr;
        right = r_expr;
        operation = opKind;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) throws ExpressionParseException {
        return visitor.visitBinaryExpression(this);
    }

    @Override
    public Expression getLeft() {
        return left;
    }

    @Override
    public Expression getRight() {
        return right;
    }

    @Override
    public BinOpKind getOperation() {
        return operation;
    }
}
