package org.fastpoke.jcalc;

public class UnknownFunctionException extends ParserException {
    public UnknownFunctionException(String functionName) {
        super("unknown function: " + functionName);
    }
}
