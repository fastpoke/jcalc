package org.fastpoke.jcalc;

public class PrematureEndOfFileException extends ParserException {
    public PrematureEndOfFileException() {
        super("premature end of file");
    }
}
