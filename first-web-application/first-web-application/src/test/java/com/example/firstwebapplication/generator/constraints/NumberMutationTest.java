package com.example.firstwebapplication.generator.constraints;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("Duplicates")
class NumberMutationTest {

    private String mutatedNumberAsString = "";

    @Rule
    private final ExpectedException expectedException = ExpectedException.none();

    @BeforeEach
    void setUp() {
        mutatedNumberAsString = "";
    }


    @Test
    void testMutateOptions() {
        assertTrue(NumberMutation.mutate(
                Mutation.CREATE_NEGATIVE_INTEGER).startsWith("-"));
        assertFalse(NumberMutation.mutate(
                Mutation.CREATE_POSITIVE_FLOATING_POINT).startsWith("-"));

        assertTrue(NumberMutation.mutate(
                Mutation.CREATE_NEGATIVE_INTEGER_WITH_COMMA).startsWith("-"));
        assertFalse(NumberMutation.mutate(Mutation.CREATE_POSITIVE_FLOATING_POINT_WITH_COMMA).startsWith("-"));

        assertTrue(NumberMutation.mutate(
                Mutation.CREATE_SCIENTIFIC_NOTATION_NUMBER).contains("E"));

        assertEquals("0", NumberMutation.mutate(Mutation.CREATE_ZERO));
    }


    @Test
    void testGetNegativeInteger() {
        mutatedNumberAsString = NumberMutation.createNegativeInteger();
        assertTrue(mutatedNumberAsString.startsWith("-"));
        assertTrue(Integer.parseInt(mutatedNumberAsString) < 0);
    }


    @Test
    void testGetPositiveInteger() {
        mutatedNumberAsString = NumberMutation.createPositiveInteger();
        assertFalse(mutatedNumberAsString.startsWith("-"));
        assertTrue(Integer.parseInt(mutatedNumberAsString) >= 0);
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testOverflowPositiveInt() {
        Assertions.assertThrows(NumberFormatException.class,
                () -> Integer.parseInt(NumberMutation.createPositiveOverflowInteger()));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testOverflowNegativeInt() {
        Assertions.assertThrows(NumberFormatException.class,
                () -> Integer.parseInt(NumberMutation.createNegativeOverflowInteger()));
    }


    @Test
    void testGetNagativeFloatingPoint() {
        mutatedNumberAsString = NumberMutation.createNegativeFloatingPoint();
        assertTrue(mutatedNumberAsString.startsWith("-"));
        assertTrue(Double.parseDouble(mutatedNumberAsString) < 0);
    }


    @Test
    void testGetPositiveFloatingPoint() {
        mutatedNumberAsString = NumberMutation.createPositiveFloatingPoint();
        assertFalse(mutatedNumberAsString.startsWith("-"));
        assertTrue(Double.parseDouble(mutatedNumberAsString) >= 0);
    }


    @Test
    void testGetNegativeIntegerWithComma() {
        mutatedNumberAsString = NumberMutation.createNegativeIntegerWithComma();
        assertTrue(mutatedNumberAsString.startsWith("-"));
        int lengthOfMutatedNumberAsString = mutatedNumberAsString.length();
        int commaCount = 1;

        for(int i = lengthOfMutatedNumberAsString - 1; i > 0; i--) {
            if(i == lengthOfMutatedNumberAsString - commaCount * 4) {
                assertEquals(',', mutatedNumberAsString.charAt(i));
                commaCount++;
            }
            else {
                assertTrue(Character.isDigit(mutatedNumberAsString.charAt(i)));
            }
        }
    }


    @Test
    void testGetPositiveIntegerWithComma() {
        mutatedNumberAsString = NumberMutation.createPositiveIntegerWithComma();
        int lengthOfMutatedNumberAsString = mutatedNumberAsString.length();
        int commaCount = 1;

        for(int i = lengthOfMutatedNumberAsString - 1; i >= 0; i--) {
            if(i == lengthOfMutatedNumberAsString - commaCount * 4) {
                assertEquals(',', mutatedNumberAsString.charAt(i));
                commaCount++;
            }
            else {
                assertTrue(Character.isDigit(mutatedNumberAsString.charAt(i)));
            }
        }
    }


    @Test
    void testGetNegativeFloatingPointWithComma() {
        mutatedNumberAsString = NumberMutation.createNegativeFloatingPointWithComma();
        assertTrue(mutatedNumberAsString.startsWith("-"));
        assertTrue(mutatedNumberAsString.contains("."));
        assertEquals(mutatedNumberAsString.length() - 1,
                mutatedNumberAsString.replaceAll("\\.", "").length());

        int startIntegerLocation = 0;
        int commaCount = 1;
        int decimalLocation = mutatedNumberAsString.indexOf(".");

        for(int i = mutatedNumberAsString.length() - 1; i > 0; i--) {
            if(i > decimalLocation) {
                assertTrue(Character.isDigit(mutatedNumberAsString.charAt(i)));
            }
            else if(i == decimalLocation) {
                startIntegerLocation = i - 1;
            }
            else {
                if (i == startIntegerLocation - commaCount * 4 + 1) {
                    assertEquals(',', mutatedNumberAsString.charAt(i));
                    commaCount++;
                }
                else {
                    assertTrue(Character.isDigit(mutatedNumberAsString.charAt(i)));
                }
            }
        }
    }


    @Test
    void testGetPositiveFloatingPointWithComma() {
        mutatedNumberAsString = NumberMutation.createPositiveFloatingPointWithComma();
        assertFalse(mutatedNumberAsString.startsWith("-"));
        assertTrue(mutatedNumberAsString.contains("."));
        assertEquals(mutatedNumberAsString.length() - 1,
                mutatedNumberAsString.replaceAll("\\.", "").length());

        int startIntegerLocation = 0;
        int commaCount = 1;
        int decimalLocation = mutatedNumberAsString.indexOf(".");

        for(int i = mutatedNumberAsString.length() - 1; i >= 0; i--) {
            if(i > decimalLocation) {
                assertTrue(Character.isDigit(mutatedNumberAsString.charAt(i)));
            }
            else if(i == decimalLocation) {
                startIntegerLocation = i - 1;
            }
            else {
                if (i == startIntegerLocation - commaCount * 4 + 1) {
                    assertEquals(',', mutatedNumberAsString.charAt(i));
                    commaCount++;
                }
                else {
                    assertTrue(Character.isDigit(mutatedNumberAsString.charAt(i)));
                }
            }
        }
    }


    @Test
    void testGetScientificNotationNumber() {
        mutatedNumberAsString = NumberMutation.createScientificNotationNumber();
        assertTrue(mutatedNumberAsString.contains("E"));
        assertEquals(mutatedNumberAsString.length() - 1,
                mutatedNumberAsString.replaceAll("E", "").length());

        if(mutatedNumberAsString.contains("-")) {
            assertTrue(mutatedNumberAsString.startsWith("-"));
            assertEquals(mutatedNumberAsString.length() - 1,
                    mutatedNumberAsString.replaceAll("-", "").length());
        }
    }


    @Test
    void testGetZero() {
        assertEquals("0", NumberMutation.createZero());
    }


    @Test
    void testConvertToEuropeanStyle() {
        String number = "1234.56";
        String europeanStyle = NumberMutation.convertToEuropeanStyle(number);
        assertEquals("1.234,56", europeanStyle);

        number = "-1234567.890";
        europeanStyle = NumberMutation.convertToEuropeanStyle(number);
        assertEquals("-1.234.567,89", europeanStyle);
    }

}