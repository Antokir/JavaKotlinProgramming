package com.lab;

public class ToStringVisitor implements ExpressionVisitor {

    private ToStringVisitor() {}

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        String left = (String) expr.getLeft().accept(this);
        String right = (String) expr.getRight().accept(this);
        BinOpKind operation = expr.getOperation();
        switch (operation) {
            case ADD -> {
                return left + " + " + right;
            }
            case SUB -> {
                return left + " - " + right;
            }
            case MUL -> {
                return left + " * " + right;
            }
            case DIV -> {
                return left + " / " + right;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return Double.toString(expr.getValue());
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) throws ExpressionParseException {
        return "(" + expr.getExpr().accept(this) + ")";
    }

    @Override
    public Object visitVariable(Variable expr) {
        return expr.getVariable();
    }

    public static final ToStringVisitor INSTANCE = new ToStringVisitor();
}
