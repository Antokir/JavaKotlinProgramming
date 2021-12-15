package com.lab;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TestsException extends Exception {
    public TestsException(String message) {
        super(message);
    }
}

class CalculatingTests {
    public static void Test1(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(2 + 1) * 3 - 5 / 7";
        Expression expression = parser.parseExpression(Tests.Parsing(string));
        double result = (Double) expression.accept(new ComputeExpressionVisitor(null));
        double answer = (2 + 1) * 3 - 5.0 / 7;
        System.out.println("CalculatingTest1: " + result + " " + answer);
        if (result - answer > 1e-6) {
            throw new TestsException("Wrong answer on Calculating test1.");
        }
        System.out.println("Test1 is accepted.");
    }

    public static void Test2(Parser parser, double x, double y, double z)
            throws ExpressionParseException, TestsException {
        String string = "(3 + 1) *  3 - 5   / (   (7 -    x) *    500 / y) -  22 * z + 2";
        double result = Tests.ParsingForCalculatingTests(string, parser, x, y, z);
        double answer = (3 + 1) * 3 - 5 / ((7 - x) * 500 / y) - 22 * z + 2;
        System.out.println("CalculatingTest2: " + result + " " + answer);
        if (result - answer > 1e-6) {
            throw new TestsException("Wrong answer on Calculating test2.");
        }
        System.out.println("Test2 is accepted.");
    }

    public static void Test3(Parser parser, double x, double y, double z)
            throws ExpressionParseException, TestsException {
        String string = "x * y * z * ((3 + 1) * 3 - 5 / " +
                "((7 - x) * 500 / y) - 22 * z + 2) / ((2 + 1) * 3 - 5 * y / 7)";
        double result = Tests.ParsingForCalculatingTests(string, parser, x, y, z);
        double answer = x * y * z * ((3 + 1) * 3 - 5 /
                ((7 - x) * 500 / y) - 22 * z + 2) / ((2 + 1) * 3 - 5 * y / 7);
        System.out.println("CalculatingTest3: " + result + " " + answer);
        if (result - answer > 1e-6) {
            throw new TestsException("Wrong answer on Calculating test3.");
        }
        System.out.println("Test3 is accepted.");
    }
}

class ToStringTests {
    public static void Test1(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(2.0 + 1.0) * 3.0 - 5.0 / 7.0";
        String result = Tests.ParsingForToStringTests(string, parser);
        if (!result.equals(string)) {
            throw new TestsException("Wrong answer on ToString test1.");
        }
        System.out.println("Test1 is accepted.");
    }

    public static void Test2(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(3.0 + 1.0) * 3.0 - 5.0 / " +
                "((7.0 - x) * 500.0 / y) - 22.0 * z + 2.0";
        String result = Tests.ParsingForToStringTests(string, parser);
        if (!result.equals(string)) {
            throw new TestsException("Wrong answer on ToString test2.");
        }
        System.out.println("Test2 is accepted.");
    }

    public static void Test3(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "x * y * z * ((3.0 + 1.0) * 3.0 - 5.0 / " +
                "((7.0 - x) * 500.0 / y) - 22.0 * z + 2.0) / " +
                "((2.0 + 1.0) * 3.0 - 5.0 * y / 7.0)";
        String result = Tests.ParsingForToStringTests(string, parser);
        if (!result.equals(string)) {
            throw new TestsException("Wrong answer on ToString test3.");
        }
        System.out.println("Test3 is accepted.");
    }
}

class TreeTests {
    public static void Test1(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(2 + x) * x - y";
        String answer = "sub(mul(paren-expr(add('2.0', var[x])), var[x]), var[y])";
        String result = Tests.ParsingForTreeTests(string, parser);
        if (!result.equals(answer)) {
            throw new TestsException("Wrong answer on Tree test1.");
        }
        System.out.println("Test1 is accepted.");
    }

    public static void Test2(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(((2 + x) * x - y) * x) * y";
        String answer = "mul(paren-expr(mul(paren-expr(sub(mul(paren-expr(" +
                "add('2.0', var[x])), var[x]), var[y])), var[x])), var[y])";
        String result = Tests.ParsingForTreeTests(string, parser);
        if (!result.equals(answer)) {
            throw new TestsException("Wrong answer on Tree test2.");
        }
        System.out.println("Test2 is accepted.");
    }

    public static void Test3(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(((1 / 1 * 1 / 1 * 1)))";
        String answer = "paren-expr(paren-expr(paren-expr(" +
                "mul(div(mul(div('1.0', '1.0'), '1.0'), '1.0'), '1.0'))))";
        String result = Tests.ParsingForTreeTests(string, parser);
        if (!result.equals(answer)) {
            throw new TestsException("Wrong answer on Tree test3.");
        }
        System.out.println("Test3 is accepted.");
    }
}

class ExceptionTests {
    public static void Test1(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "((2 + x))) * x - y";
        Tests.ParsingForTreeTests(string, parser);
    }

    public static void Test2(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(2 + x) * * x - y";
        Tests.ParsingForTreeTests(string, parser);
    }

    public static void Test3(Parser parser)
            throws ExpressionParseException, TestsException {
        String string = "(2 + 1) * 1 - 1 / 0";
        Expression expression = parser.parseExpression(Tests.Parsing(string));
        expression.accept(new ComputeExpressionVisitor(null));
    }
}

public class Tests {
    public static void main(String[] args)
            throws ExpressionParseException, TestsException {
        Parser parser = new ParserImpl();
        try {
            System.out.println("Calculating tests, part 1.");
            CalculatingTests.Test1(parser);
            CalculatingTests.Test2(parser, 1.0, 2.0, 3.0);
            CalculatingTests.Test3(parser, 1.0, 2.0, 3.0);
            System.out.println("Calculating tests are accepted.");
            System.out.println();

            System.out.println("Calculating tests, part 2.");
            CalculatingTests.Test1(parser);
            CalculatingTests.Test2(parser, 1.5, 12.0, 3.9);
            CalculatingTests.Test3(parser, 1.8, 2.2, 5.0);
            System.out.println("Calculating tests are accepted.");
            System.out.println();

            System.out.println("ToString tests.");
            ToStringTests.Test1(parser);
            ToStringTests.Test2(parser);
            ToStringTests.Test3(parser);
            System.out.println("ToString tests are accepted.");
            System.out.println();

            System.out.println("Tree tests");
            TreeTests.Test1(parser);
            TreeTests.Test2(parser);
            TreeTests.Test3(parser);
            System.out.println("Tree tests are accepted.");
            System.out.println();

        } catch (TestsException | ExpressionParseException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Exception tests");
        boolean accepted = false;
        try {
            ExceptionTests.Test1(parser);
        } catch (ExpressionParseException | ArithmeticException e) {
            System.out.println("Test1 is accepted");
            accepted = true;
        } finally {
            if (!accepted) {
                System.err.println("Exception tests are failed");
            }
            accepted = false;
            try {
                ExceptionTests.Test2(parser);
            } catch (ExpressionParseException | ArithmeticException e) {
                System.out.println("Test2 is accepted");
                accepted = true;
            } finally {
                if (!accepted) {
                    System.err.println("Exception tests are failed");
                }
                accepted = false;
                try {
                    ExceptionTests.Test3(parser);
                } catch (ExpressionParseException | ArithmeticException e) {
                    System.out.println("Test3 is accepted");
                    accepted = true;
                } finally {
                    if (!accepted) {
                        System.err.println("Exception tests are failed");
                    } else {
                        System.out.println("Exception tests are accepted");
                    }
                }
            }
        }

    }

    static String Parsing(String string) {
        Scanner scanner = new Scanner(string);
        char[] str_expr = scanner.nextLine().toCharArray();
        StringBuilder right_str = new StringBuilder();
        for (char symbol : str_expr) {
            if (symbol == '(' || symbol == ')') {
                right_str.append(' ').append(symbol).append(' ');
                continue;
            }
            right_str.append(symbol);
        }
        return right_str.toString();
    }

    static double ParsingForCalculatingTests(String string, Parser parser,
                                             double x, double y, double z)
            throws ExpressionParseException {

        Expression expression = parser.parseExpression(Tests.Parsing(string));
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        return (Double) expression.accept(new ComputeExpressionVisitor(map));
    }

    static String ParsingForToStringTests(String string, Parser parser)
            throws ExpressionParseException {
        Expression expression = parser.parseExpression(Tests.Parsing(string));
        return (String) expression.accept(ToStringVisitor.INSTANCE);
    }

    static String ParsingForTreeTests(String string, Parser parser)
            throws ExpressionParseException {
        Expression expression = parser.parseExpression(Tests.Parsing(string));
        return (String) expression.accept(DebugRepresentationExpressionVisitor.INSTANCE);
    }
}
