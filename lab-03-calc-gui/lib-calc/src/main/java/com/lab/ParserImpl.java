package com.lab;

import java.util.Scanner;
import java.util.Stack;

public class ParserImpl implements Parser {

    @Override
    public Expression parseExpression(String input)
            throws ExpressionParseException {
        // poland notation
        Scanner scanner = new Scanner(input);
        StringBuilder pol_notation = new StringBuilder();
        Stack<String> stack = new Stack<>();
        while (scanner.hasNext()) {
            String string = scanner.next();
            if (IsOperation(string)) {
                if (string.equals("(")) {
                    stack.push("()");
                    stack.push("(");
                } else if (string.equals(")")) {
                    while (!stack.empty() && !stack.peek().equals("(")) {
                        if (stack.peek().equals(")")) {
                            throw new ExpressionParseException("invalid expression");
                        }
                        pol_notation.append(" ").append(stack.peek());
                        stack.pop();
                    }
                    if (stack.empty()) {
                        throw new ExpressionParseException("invalid expression");
                    }
                    stack.pop();
                } else {
                    while (!stack.empty() && Priority(string) <= Priority(stack.peek())) {
                        pol_notation.append(" ").append(stack.peek());
                        stack.pop();
                    }
                    stack.push(string);
                }
            } else {
                pol_notation.append(" ").append(string);
            }
        }
        while (!stack.empty()) {
            if (!IsOperation(stack.peek())) {
                throw new ExpressionParseException("invalid expression");
            }
            pol_notation.append(" ").append(stack.peek());
            stack.pop();
        }

        // making tree

        Stack<Expression> expressions = new Stack<>();
        Scanner scanner2 = new Scanner(pol_notation.toString());

        while (scanner2.hasNext()) {
            String string = scanner2.next();
            if (IsOperation(string)) {
                if (expressions.empty()) {
                    throw new ExpressionParseException("invalid expression");
                }
                Expression second = expressions.peek();
                expressions.pop();

                // parenthesis expression
                if (string.equals("()")) {
                    expressions.push(new ParenthesisExpressionImpl(second));
                    continue;
                }

                if (expressions.empty()) {
                    throw new ExpressionParseException("invalid expression");
                }
                Expression first = expressions.peek();
                expressions.pop();
                expressions.push(new BinaryExpressionImpl(first, second,
                        BinaryOperation(string)));
            } else if (IsLiteral(string)) {
                Literal literal = new LiteralImpl(Double.parseDouble(string));
                expressions.push(literal);
            } else if (IsVariable(string)) {
                Variable variable = new VariableImpl(string);
                expressions.push(variable);
            } else {
                throw new ExpressionParseException("invalid expression");
            }
        }
        if (expressions.size() > 1) {
            throw new ExpressionParseException("invalid expression");
        }

        return expressions.peek();
    }

    boolean IsOperation(String string) {
        return string.equals("+") || string.equals("-") ||
                string.equals("*") || string.equals("/") ||
                string.equals("(") || string.equals(")") ||
                string.equals("()");
    }

    boolean IsLiteral(String string) {
        char[] char_array = string.toCharArray();
        int dots = 0;
        for (int i = 0; i < char_array.length; ++i) {
            if (char_array[i] == '.') {
                if (i == 0 || dots > 0) {
                    return false;
                }
                dots++;
                continue;
            }
            if (!(char_array[i] >= '0' && char_array[i] <= '9')) {
                return false;
            }
        }
        return true;
    }

    boolean IsVariable(String string) {
        char[] char_array = string.toCharArray();
        for (char symbol : char_array) {
            if (!((symbol >= 'a' && symbol <= 'z') ||
                    (symbol >= 'A' && symbol <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    BinOpKind BinaryOperation(String string) throws ExpressionParseException {
        switch (string) {
            case "+" -> {
                return BinOpKind.ADD;
            }
            case "-" -> {
                return BinOpKind.SUB;
            }
            case "*" -> {
                return BinOpKind.MUL;
            }
            case "/" -> {
                return BinOpKind.DIV;
            }
            default -> {
                throw new ExpressionParseException("invalid expression");
            }
        }
    }

    int Priority(String string) {
        return switch (string) {
            case "()" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }
}






