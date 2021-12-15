package com.lab;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;
        Expression expression;
        boolean except = true;
        while (except) {
            except = false;
            try {
                Parser parser = new ParserImpl();
                System.out.print("enter expression: ");
                scanner = new Scanner(System.in);
                char[] str_expr = scanner.nextLine().toCharArray();
                StringBuilder right_str = new StringBuilder();
                // adding spaces
                for (char symbol : str_expr) {
                    if (symbol == '(' || symbol == ')') {
                        right_str.append(' ').append(symbol).append(' ');
                        continue;
                    }
                    right_str.append(symbol);
                }
                expression = parser.parseExpression(right_str.toString());

                String debug = (String) expression.accept(
                                DebugRepresentationExpressionVisitor.INSTANCE);
                int depth = (Integer) expression.accept(
                        DepthExpressionVisitor.INSTANCE);
                System.out.println("tree: " + debug);
                System.out.println("expr-tree depth: " + depth);

                // set of variables
                Map<String, Double> map =
                        (Map<String, Double>) expression.accept(
                                new VariablesExpressionVisitor(scanner));
                double result = (Double) expression.accept(
                        new ComputeExpressionVisitor(map));
                System.out.println("result: " + result);
                String to_string = (String) expression.accept(
                        ToStringVisitor.INSTANCE);
                System.out.println(to_string);

            } catch (ExpressionParseException | ArithmeticException exception) {
                except = true;
                System.out.println("You have " + exception.getMessage() + ", " +
                        "please try again with correct expression.");
            }
        }
    }
}
