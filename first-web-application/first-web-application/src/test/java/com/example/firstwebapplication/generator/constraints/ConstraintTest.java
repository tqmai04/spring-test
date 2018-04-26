package com.example.firstwebapplication.generator.constraints;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ConstraintTest {

    @Test
    void testToStringSingleParameter() {
        Constraint constraint = new Constraint("zip",
                Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "1");
        String expected = String.format("zip | %d | 1", Mutation.TRUNCATE_NUMBER_OF_CHARACTERS);
        String actual = constraint.toString();

        assertEquals(expected, actual);
    }


    @Test
    void testToStringMultipleParameter() {
        Constraint constraint = new Constraint("zip",
                Mutation.REPLACE_CHARACTER, "1", "2");
        String expected = String.format("zip | %d | 1 | 2", Mutation.REPLACE_CHARACTER);
        String actual = constraint.toString();

        assertEquals(expected, actual);
    }

}