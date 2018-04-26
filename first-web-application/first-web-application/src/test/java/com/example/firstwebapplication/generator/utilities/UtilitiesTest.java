package com.example.firstwebapplication.generator.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UtilitiesTest {

    @Test
    void testIsStringURI() {
        assertTrue(Utilities.isStringURI("http://purl.org/dc/terms/subject"));
        assertTrue(Utilities.isStringURI("https://www.maharashtra.gov.in/"));
        assertTrue(Utilities.isStringURI("http://localhost:3030/dataset.html?tab=query&ds=/ObjectWithURI"));
    }

    @Test
    void testIsStringNotURI() {
//        assertFalse(Utilities.isStringURI("http://http://purl.something/dc/terms/subject"));
        assertFalse(Utilities.isStringURI("https: //www.maharashtra.gov.in/with-space"));
        assertFalse(Utilities.isStringURI("localhost:3030/dataset.html?tab=query&ds=/ObjectWithURI"));
    }


    @Test
    void testDoesStringContainURI() {
        assertTrue(Utilities.doesStringContainURI("\"0.572\"^^<http://www.w3.org/2001/XMLSchema#double>"));
        assertTrue(Utilities.doesStringContainURI("0.572^^<http://www.w3.com/2001/XMLSchema#double>"));
        assertTrue(Utilities.doesStringContainURI("http://http://purl.org/dc/terms/subject"));
    }


    @Test
    void testDoesStringNotContainURI() {
        assertFalse(Utilities.doesStringContainURI("\"0.572\""));
        assertFalse(Utilities.doesStringContainURI("abc"));
//        assertFalse(Utilities.doesStringContainURI("http://http://purl.org/dc/terms/subject"));
    }


    @Test
    void testExtractURIFromString() {
        // without quote and without ^^
        String stringWithURI = "0.572<http://www.w3.org/2001/XMLSchema#double>";
        String expected = "0.572";
        String actual = Utilities.extractURIFromString(stringWithURI);
        assertEquals(expected, actual);

        // without quote and with ^^
        stringWithURI = "0.572^^<http://www.w3.org/2001/XMLSchema#double>";
        actual = Utilities.extractURIFromString(stringWithURI);
        assertEquals(expected, actual);

        // with quote and with ^^
        stringWithURI = "\"0.572\"^^<http://www.w3.org/2001/XMLSchema#double>";
        actual = Utilities.extractURIFromString(stringWithURI);
        assertEquals(expected, actual);
    }


}