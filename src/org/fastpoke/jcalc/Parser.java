package org.fastpoke.jcalc;


import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

        double result = number(in, multTerminators);
        while (true) {
            int c = read(in);
            if (terminators.contains(c)) {
                in.reset();
                break;
            }
            if (c != '*' && c != '/') {
                throw new ParserException("unexpected term-level operation: " + (char) c);
            }
            double multValue = number(in, multTerminators);
            if (c == '*') {
                result = result * multValue;
            } else {
                result = result / multValue;
            }
        }
        return result;
    }

    static double number(Reader in, Set<Integer> terminators) throws IOException, ParserException {
        double r = 0;
        while (true) {
            int c = read(in);
            if (terminators.contains(c)) {
                in.reset();
                break;
            }
            if (c < '0' || c > '9') {
                throw new ParserException("unexpected digit: " + (char) c);
            }
            r = r * 10 + (Character.digit(c, 10));
        }
        return r;
    }

}
