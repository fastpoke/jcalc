package org.fastpoke.jcalc;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/*
> expr = term *(("+" | "-") term)
term = [-] mult *(("*"|"/") mult)
mult = number | "(" expr ")" | function
> function = fname "(" expr *( "," expr ) ")"
 */
public class Parser {

    public static double parse(String expression) throws ParserException {
        try {
            return parse(new StringReader(expression));
        } catch (IOException e) {
            throw new AssertionError("wtf? io exception from string reader", e);
        }
    }

    public static double parse(Reader in) throws IOException, ParserException {
        return expr(in, Collections.singleton(-1));
    }

    private static int read(Reader in) throws IOException {
        while (true) {
            in.mark(1);
            int c = in.read();
            if (c == -1 || !Character.isWhitespace((char) c)) {
                return c;
            }
        }
    }

    private static boolean isDigit(int c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isFunctionNameCharacter(int c) {
        return c >= 'a' && c <= 'z';
    }

    static double expr(Reader in, Set<Integer> terminators) throws ParserException, IOException {
        Set<Integer> termTerminators = new HashSet<>(terminators);     //generics
        termTerminators.add((int) '+');
        termTerminators.add((int) '-');

        double result = term(in, termTerminators);
        while (true) {
            int c = read(in);
            if (terminators.contains(c)) {
                in.reset();
                break;
            }
            if (c != '+' && c != '-') {
                throw new ParserException("unexpected expr-level operation: " + (char) c);
            }
            result = result + (c == '+' ? 1 : -1) * term(in, termTerminators);
        }
        return result;
    }

    static double term(Reader in, Set<Integer> terminators) throws ParserException, IOException {
        Set<Integer> multTerminators = new HashSet<>(terminators);
        multTerminators.add((int) '*');
        multTerminators.add((int) '/');

        double result = mult(in, multTerminators);
        while (true) {
            int c = read(in);
            if (terminators.contains(c)) {
                in.reset();
                break;
            }
            if (c != '*' && c != '/') {
                throw new ParserException("unexpected term-level operation: " + (char) c);
            }
            double multValue = mult(in, multTerminators);
            if (c == '*') {
                result = result * multValue;
            } else {
                result = result / multValue;
            }
        }
        return result;
    }

    static double mult(Reader in, Set<Integer> terminators) throws ParserException, IOException {
        int c = read(in);
        in.reset();
        if (c == -1) {
            throw new PrematureEndOfFileException();
        } else if (isDigit(c)) {
            return number(in, terminators);
        } else if (c == '(') {
            return parentheses(in);
        } else if (isFunctionNameCharacter(c)) {
            return function(in);
        } else {
            throw new UnexpectedSymbolException(c);
        }
    }

    static double number(Reader in, Set<Integer> terminators) throws IOException, ParserException {
        double r = 0;
        while (true) {
            int c = read(in);
            if (terminators.contains(c)) {
                in.reset();
                break;
            }
            if (!isDigit(c)) {
                throw new ParserException("unexpected digit: " + (char) c);
            }
            r = r * 10 + (Character.digit(c, 10));
        }
        return r;
    }

    static double parentheses(Reader in) throws IOException, ParserException {
        int c = read(in);
        if (c != '(') {
            throw new UnexpectedSymbolException(c);
        }
        double result = expr(in, Collections.singleton((int) ')'));
        if (read(in) != ')') {
            throw new UnexpectedSymbolException(c);
        }
        return result;
    }

    static double function(Reader in) throws ParserException, IOException {
        StringBuilder nameAccumulator = new StringBuilder();
        int c;
        while (true) {
            c = read(in);
            if (c == '(') {
                break;
            }
            if (!isFunctionNameCharacter(c)) {
                throw new ParserException("unexpected symbol in function name: " + (char) c);
            }
            nameAccumulator.append((char) c);
        }
        List<Double> args = new ArrayList<>();
        Set<Integer> terminators = new HashSet<>(Arrays.asList((int) ',', (int) ')'));
        do {
            args.add(expr(in, terminators));
            c = read(in);
        } while (c == ',');
        String name = nameAccumulator.toString();
        switch (name) {
            case "sqrt":
                return Math.sqrt(args.get(0));
            default:
                throw new UnknownFunctionException(name);
        }
    }

}
