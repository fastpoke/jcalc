package org.fastpoke.jcalc;


import java.io.IOException;
import java.io.Reader;
import java.util.*;

/*
expr = term *(("+" | "-") term)
term = [-] mult *(("*"|"/") mult)
mult = number | "(" expr ")" | function
function = fname "(" expr *( "," expr ) ")"
 */
public class Parser {

    public static double expr(Reader in) throws IOException, ParserException {
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
        } else {
            return function(in);
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

    static double function(Reader in) throws ParserException, IOException {
        StringBuilder name = new StringBuilder();
        int c;
        while (true) {
            c = read(in);
            if (c == '(') {
                break;
            }
            if (c < 'a' || c > 'z') {
                throw new ParserException("unexpected symbol in function name: " + (char) c);
            }
            name.append((char) c);
        }
        List<Double> args = new ArrayList<>();
        //dirty hack >_<!
        Set<Integer> terminators = new HashSet<>(Arrays.asList((int) ',', (int) ')'));
        do {
            args.add(expr(in, terminators));
            c = read(in);
        } while (c == ',');
        switch (name.toString()) {
            case "sqrt":
                return Math.sqrt(args.get(0));
            default:
                throw new ParserException("unknown function: " + name);
        }
    }

}
