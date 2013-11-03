package org.fastpoke.jcalc;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ParserTest {

    public static final double EPSILON = 1e-10;

    @Test
    public void number() throws ParserException {
        assertEquals(Parser.parse("2"), 2.0, EPSILON);
    }

    @Test
    public void twoPlusTwo() throws ParserException {
        assertEquals(Parser.parse("2+2"), 4.0, EPSILON);
    }

    @Test
    public void twoPlusTwoWithSpaces() throws ParserException {
        assertEquals(Parser.parse("2  +   2"), 4.0, EPSILON);
    }

    @Test
    public void twoMinusTwo() throws ParserException {
        assertEquals(Parser.parse("2-2"), 0.0, EPSILON);
    }

    @Test
    public void twoTimesTwo() throws ParserException {
        assertEquals(Parser.parse("2*2"), 4.0, EPSILON);
    }

    @Test
    public void twoOverTwo() throws ParserException {
        assertEquals(Parser.parse("2/2"), 1.0, EPSILON);
    }

    @Test
    public void sqrtOfFour() throws ParserException {
        assertEquals(Parser.parse("sqrt(4)"), 2.0, EPSILON);
    }

//WOW SUCH TDD
    @Test(expectedExceptions = PrematureEndOfFileException.class)
    public void empty() throws ParserException {
        Parser.parse("");
    }

    @Test(expectedExceptions = PrematureEndOfFileException.class)
    public void twoPlusNothing() throws ParserException {
        Parser.parse("2+");
    }

    @Test(expectedExceptions = UnexpectedSymbolException.class)
    public void nothingPlusTwo() throws ParserException {
        Parser.parse("+2");
    }

    @Test(expectedExceptions = UnknownFunctionException.class)
    public void unknownFunction() throws ParserException {
        Parser.parse("desu(2, 2)");
    }

    @Test(expectedExceptions = UnexpectedSymbolException.class)
    public void closingParenthesis() throws ParserException {
        Parser.parse(")desu");
    }

    @Test
    public void negative() throws ParserException {
        assertEquals(Parser.parse("-1"), -1.0, EPSILON);
    }
}
