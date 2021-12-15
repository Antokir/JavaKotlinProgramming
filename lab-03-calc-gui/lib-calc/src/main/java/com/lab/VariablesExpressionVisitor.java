package com.lab;

import java.util.*;

public class VariablesExpressionVisitor implements ExpressionVisitor {

    private final Scanner scanner;
    private final Map<String, Double> variables;

    public VariablesExpressionVisitor(Scanner scan) {
        scanner = scan;
        variables = new HashMap<>();
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        expr.getLeft().accept(this);
        expr.getRight().accept(this);
        return variables;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return variables;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) throws ExpressionParseException {
        return expr.getExpr().accept(this);
    }

    @Override
    public Object visitVariable(Variable expr) {
        if (!variables.containsKey(expr.getVariable())) {
            System.out.print("value for " + "'" + expr.getVariable() + "': ");
            variables.put(expr.getVariable(), scanner.nextDouble());
        }
        return variables;
    }
}
