package org.fastpoke.jcalc;

public class UnexpectedSymbolException extends ParserException {
    public UnexpectedSymbolException(int c) {
        super("unexpected symbol: " + (char) c);
    }
}
