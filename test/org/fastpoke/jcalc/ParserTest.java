package org.fastpoke.jcalc;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.fastpoke.jcalc.Parser.expr;
import static org.testng.Assert.assertEquals;

public class ParserTest {

    public static final double EPSILON = 1e-10;

    @Test
    public void number() throws IOException, ParserException {
        assertEquals(Parser.parse("2"), 2.0, EPSILON);
    }

    @Test
    public void twoPlusTwo() throws IOException, ParserException {
        assertEquals(Parser.parse("2+2"), 4.0, EPSILON);
    }

    @Test
    public void twoPlusTwoWithSpaces() throws IOException, ParserException {
        assertEquals(Parser.parse("2  +   2"), 4.0, EPSILON);
    }

    @Test
    public void twoMinusTwo() throws IOException, ParserException {
        assertEquals(Parser.parse("2-2"), 0.0, EPSILON);
    }

    @Test
    public void twoTimesTwo() throws IOException, ParserException {
        assertEquals(Parser.parse("2*2"), 4.0, EPSILON);
    }

    @Test
    public void twoOverTwo() throws IOException, ParserException {
        assertEquals(Parser.parse("2/2"), 1.0, EPSILON);
    }

    @Test
    public void sqrtOfFour() throws IOException, ParserException {
        assertEquals(Parser.parse("sqrt(4)"), 2.0, EPSILON);
    }

//WOW SUCH TDD
    @Test(expectedExceptions = PrematureEndOfFileException.class)
    public void empty() throws IOException, ParserException {
        Parser.parse("");
    }

    @Test(expectedExceptions = PrematureEndOfFileException.class)
    public void twoPlusNothing() throws IOException, ParserException {
        Parser.parse("2+");
    }

    @Test(expectedExceptions = UnexpectedSymbolException.class)
    public void nothingPlusTwo() throws IOException, ParserException {
        Parser.parse("+2");
    }

    @Test(expectedExceptions = UnknownFunctionException.class)
    public void unknownFunction() throws IOException, ParserException {
        Parser.parse("desu(2, 2)");
    }

}
