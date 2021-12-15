package com.lab;

public interface ExpressionVisitor {
    Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException;
    Object visitLiteral(Literal expr);
    Object visitParenthesis(ParenthesisExpression expr) throws ExpressionParseException;
    Object visitVariable(Variable expr) throws ExpressionParseException;
}
