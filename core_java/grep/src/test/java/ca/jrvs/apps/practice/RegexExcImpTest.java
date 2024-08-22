package ca.jrvs.apps.practice;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexExcImpTest {
    private RegexExcImp regexExcImp;

    @Before
    public void setup() {
        regexExcImp = new RegexExcImp();
    }

    @Test
    public void validJpegFirstFormat() {
        assertTrue(regexExcImp.matchJpeg("hello.jpg"));
    }

    @Test
    public void validJpegSecondFormat() {
        assertTrue(regexExcImp.matchJpeg("hello.jpeg"));
    }

    @Test
    public void invalidJpegFormat() {
        assertFalse(regexExcImp.matchJpeg("hello.png"));
    }

    @Test
    public void missingJpegFormat() {
        assertFalse(regexExcImp.matchJpeg("hello"));
    }

    @Test
    public void extraJpegFormat() {
        assertTrue(regexExcImp.matchJpeg("hello.jpg.jpg"));
    }

    @Test
    public void validLowerLimitIp() {
        assertTrue(regexExcImp.matchIp("0.0.0.0"));
    }

    @Test
    public void validUpperLimitIp() {
        assertTrue(regexExcImp.matchIp("999.999.999.999"));
    }

    @Test
    public void validRandomLimitIp() {
        assertTrue(regexExcImp.matchIp("0.12.312.3"));
    }

    @Test
    public void invalidIpFormat() {
        assertFalse(regexExcImp.matchIp("0.0.0.0.0"));
    }

    @Test
    public void exceedIpRange() {
        assertFalse(regexExcImp.matchIp("0000.0.0.0"));
    }

    @Test
    public void validEmptyLine() {
        assertTrue(regexExcImp.isEmptyLine(""));
    }

    @Test
    public void validNewLine() {
        assertTrue(regexExcImp.isEmptyLine("\n"));
    }

    @Test
    public void validWhitespaceLine() {
        assertTrue(regexExcImp.isEmptyLine(" "));
    }

    @Test
    public void invalidEmptyLine() {
        assertFalse(regexExcImp.isEmptyLine("a"));
    }
}
