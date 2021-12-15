package com.lab;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalculatorImpl implements Calculator {

    @Override
    public String GetResults(String input, Map<String, Double> variables) {
        Scanner scanner;
        Expression expression;
        boolean except = true;
        Double result = null;
        String debug = null;
        Integer depth = null;
        while (except) {
            except = false;
            try {
                Parser parser = new ParserImpl();
                scanner = new Scanner(input);
                String str_expr = scanner.nextLine();
                StringBuilder right_str = new StringBuilder();

                // adding spaces
                for (int i = 0; i < str_expr.length(); ++i) {
                    right_str.append(' ');
                    right_str.append(str_expr.charAt(i));

                    if (IsDigit(str_expr.charAt(i))) {
                        while (i + 1 < str_expr.length() && (IsDigit(str_expr.charAt(i + 1)) ||
                                str_expr.charAt(i + 1) == '.')) {
                            right_str.append(str_expr.charAt(i + 1));
                            ++i;
                        }
                    } else if (IsLetter(str_expr.charAt(i))) {
                        while (i + 1 < str_expr.length() && (IsLetter(str_expr.charAt(i + 1)) ||
                                IsDigit(str_expr.charAt(i + 1)))) {
                            right_str.append(str_expr.charAt(i + 1));
                            ++i;
                        }
                    }
                }
                expression = parser.parseExpression(right_str.toString());

                depth = (Integer) expression.accept(
                        DepthExpressionVisitor.INSTANCE);
                debug = (String) expression.accept(
                        DebugRepresentationExpressionVisitor.INSTANCE);

                // set of variables
                result = (Double) expression.accept(
                        new ComputeExpressionVisitor(variables));

            } catch (ExpressionParseException exception) {
                return "";
            } catch (ArithmeticException exception) {
                return exception.getMessage();
            }
        }
        return "Result: " + result.toString() + ";\nDebug representation: " + debug +
                ";\nDepth: " + depth.toString() + ".";
    }

    boolean IsDigit(char symbol) {
        return symbol >= '0' && symbol <= '9';
    }
    boolean IsLetter(char symbol) {
        return symbol >= 'a' && symbol <= 'z' || symbol >= 'A' && symbol <= 'Z';
    }
}
