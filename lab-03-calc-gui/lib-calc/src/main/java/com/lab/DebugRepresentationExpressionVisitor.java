package com.lab;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {

    public static final DebugRepresentationExpressionVisitor INSTANCE =
            new DebugRepresentationExpressionVisitor();

    private DebugRepresentationExpressionVisitor() {
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        String l_debug = (String) expr.getLeft().accept(this);
        String r_debug = (String) expr.getRight().accept(this);
        BinOpKind operation = expr.getOperation();
        switch (operation) {
            case ADD -> {
                return "add(" + l_debug + ", " + r_debug + ")";
            }
            case SUB -> {
                return "sub(" + l_debug + ", " + r_debug + ")";
            }
            case MUL -> {
                return "mul(" + l_debug + ", " + r_debug + ")";
            }
            case DIV -> {
                return "div(" + l_debug + ", " + r_debug + ")";
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return "'" + expr.getValue() + "'";
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) throws ExpressionParseException {

        return "paren-expr(" + expr.getExpr().accept(this) + ")";
    }

    @Override
    public Object visitVariable(Variable expr) {
        return "var[" + expr.getVariable() + "]";
    }
}
