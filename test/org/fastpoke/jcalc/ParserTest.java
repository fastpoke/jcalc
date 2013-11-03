package org.fastpoke.jcalc;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.fastpoke.jcalc.Parser.expr;
import static org.testng.Assert.assertEquals;

public class ParserTest {

    public static final double EPSILON = 1e-10;

    private double parse(String expression) throws IOException, ParserException {
        return expr(new StringReader(expression));
    }

    @Test
    public void number() throws IOException, ParserException {
        assertEquals(parse("2"), 2.0, EPSILON);
    }

    @Test
    public void twoPlusTwo() throws IOException, ParserException {
        assertEquals(parse("2+2"), 4.0, EPSILON);
    }

    @Test
    public void twoPlusTwoWithSpaces() throws IOException, ParserException {
        assertEquals(parse("2  +   2"), 4.0, EPSILON);
    }

    @Test
    public void twoMinusTwo() throws IOException, ParserException {
        assertEquals(parse("2-2"), 0.0, EPSILON);
    }

    @Test
    public void twoTimesTwo() throws IOException, ParserException {
        assertEquals(parse("2*2"), 4.0, EPSILON);
    }

    @Test
    public void twoOverTwo() throws IOException, ParserException {
        assertEquals(parse("2/2"), 1.0, EPSILON);
    }

    @Test
    public void sqrtOfFour() throws IOException, ParserException {
        assertEquals(parse("sqrt(4)"), 2.0, EPSILON);
    }

//WOW SUCH TDD
    @Test(expectedExceptions = PrematureEndOfFileException.class)
    public void empty() throws IOException, ParserException {
        parse("");
    }

    @Test(expectedExceptions = PrematureEndOfFileException.class)
    public void twoPlusNothing() throws IOException, ParserException {
        parse("2+");
    }

}
