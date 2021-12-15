package com.lab;

public class DepthExpressionVisitor implements ExpressionVisitor {

    public static final DepthExpressionVisitor INSTANCE =
            new DepthExpressionVisitor();

    private DepthExpressionVisitor() {
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        return Math.max((Integer) expr.getLeft().accept(this),
                (Integer) expr.getRight().accept(this)) + 1;
    }

    @Override
    public Object visitLiteral(Literal expr) {

        return 1;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) throws ExpressionParseException {

        return 1 + (Integer) expr.getExpr().accept(this);
    }

    @Override
    public Object visitVariable(Variable expr) {
        return 1;
    }
}
