package converter.tests;

import converter.ElbonianArabicConverter;
import converter.exceptions.MalformedNumberException;
import converter.exceptions.ValueOutOfBoundsException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the ElbonianArabicConverter class.
 */
public class ConverterTests {

    @Test
    public void ElbonianToArabicSampleTest() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("1");
        assertEquals(converter.toElbonian(), "I");
    }

    @Test
    public void ArabicToElbonianSampleTest() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("I");
        assertEquals(converter.toArabic(), 1);
    }

    @Test(expected = MalformedNumberException.class)
    public void malformedNumberTest() throws MalformedNumberException, ValueOutOfBoundsException {
        throw new MalformedNumberException("TEST");
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void valueOutOfBoundsTest() throws MalformedNumberException, ValueOutOfBoundsException {
        throw new ValueOutOfBoundsException("0");
    }

    // TODO Add more test cases
    @Test
    public void ArabicToElbonianGivenTestElbonian() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("MMDZYJI");
        assertEquals(converter.toArabic(), 2394);
    }

    @Test
    public void ArabicToElbonianGivenTestArabic() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("2394");
        assertEquals(converter.toElbonian(), "MMDZYJI");
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void negativeNumberTest() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("-1");
    }

    @Test
    public void checkAll() throws ValueOutOfBoundsException, MalformedNumberException {
        HashMap<String, Integer> hashMap = new HashMap<>();
        // Check every possible arabic value
        for (int i = 1; i < 2999; i++) {
            ElbonianArabicConverter converter = new ElbonianArabicConverter(i + "");
            hashMap.put(converter.toElbonian(), i);
            assertEquals(converter.toArabic(), i);
        }
        // Check every possible elbonian value
        for (String string : hashMap.keySet()) {
            ElbonianArabicConverter converter = new ElbonianArabicConverter(string);
            int value = hashMap.get(string);
            assertEquals(value, converter.toArabic());
        }
    }

    @Test(expected = MalformedNumberException.class)
    public void checkLowercase() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("mmdzyji");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkOrderMalformed() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("MMCD");
    }

    @Test
    public void checkOrder() throws ValueOutOfBoundsException, MalformedNumberException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("MMDC");
        assertEquals(converter.toArabic(), 2400);
    }

    @Test(expected = MalformedNumberException.class)
    public void checkPairingMalformed() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("MMEDC");
    }

    @Test
    public void checkPairing() throws ValueOutOfBoundsException, MalformedNumberException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("MMEDZYKJ");
        assertEquals(converter.toArabic(), 2999);
    }

    @Test(expected = MalformedNumberException.class)
    public void checkCountMalformed() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("MMM");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkCountMalformed2() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("MMDD");
    }

    @Test
    public void checkBottomBound() throws ValueOutOfBoundsException, MalformedNumberException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("1");
        assertEquals(converter.toElbonian(), "I");
    }

    @Test
    public void checkTopBound() throws ValueOutOfBoundsException, MalformedNumberException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("2999");
        assertEquals(converter.toElbonian(), "MMEDZYKJ");
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void checkOverTopBound() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("3000");
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void checkUnderTopBound() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("0");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkEmptyString() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkSpaceString() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter(" ");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkSpaceBetween() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("9 9");
    }

    @Test
    public void checkTrim() throws ValueOutOfBoundsException, MalformedNumberException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter(" 99 ");
        assertEquals(converter.toElbonian(), "ZYKJ");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkTrimAndSpaceBetween() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter(" 9 9 ");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkSmileEmoji() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("😃");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkDecimal() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("12.5");
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void checkMaxValue() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter(Integer.MAX_VALUE + "");
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void checkMinValue() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter(Integer.MIN_VALUE + "");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkMaxDouble() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter(Double.MAX_VALUE + "");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkMinDouble() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter(Double.MIN_VALUE + "");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkCommaDecimal() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("100.0");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkRoundingDecimal() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("100.5");
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void checkComma() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("100,100");
    }

    @Test
    public void checkCommaPass() throws ValueOutOfBoundsException, MalformedNumberException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("1,129");
        assertEquals(converter.toArabic(), 1129);
    }

    @Test(expected = MalformedNumberException.class)
    public void checkNewLine() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("\n100");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkTabs() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("\t100");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkLeadingZeroes() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("01");
    }
    @Test(expected = ValueOutOfBoundsException.class)
    public void checkZero() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("0");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkBadCommas() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("1,2,3,4");
    }

    @Test(expected = MalformedNumberException.class)
    public void checkBadCommasAgain() throws ValueOutOfBoundsException, MalformedNumberException {
        new ElbonianArabicConverter("100,4");
    }
}
