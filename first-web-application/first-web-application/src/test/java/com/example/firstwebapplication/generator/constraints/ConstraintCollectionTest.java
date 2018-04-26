package com.example.firstwebapplication.generator.constraints;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ConstraintCollectionTest {

    private ConstraintCollection constraintCollection;


    @BeforeEach
    void setUp() {
        constraintCollection = new ConstraintCollection();
    }


    @AfterEach
    void tearDown() {
        constraintCollection = null;
    }


    @Test
    void testGetConstraintForSinglePredicate() {
        Constraint constraint = new Constraint("zip",
                Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "1");
        constraintCollection.addConstraint(constraint);

        String expected = constraint.toString();
        String actual = constraintCollection.getConstraintForPredicate("zip").toString();

        assertEquals(expected, actual);
    }


    @Test
    void testGetConstraintForMultiplePredicatesReuseConstraint() {
        Constraint constraint = new Constraint("zip",
                Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "1");
        constraintCollection.addConstraint(constraint);

        constraint = new Constraint("name",
                Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "2");
        constraintCollection.addConstraint(constraint);


        String expected = constraint.toString();
        String actual = constraintCollection.getConstraintForPredicate("name").toString();

        assertEquals(expected, actual);
    }


    @Test
    void testGetConstraintForMultiplePredicatesNewConstraint() {
        Constraint constraint1 = new Constraint("zip",
                Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "1");
        constraintCollection.addConstraint(constraint1);

        Constraint constraint2 = new Constraint("name",
                Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "2");
        constraintCollection.addConstraint(constraint2);


        String expected = constraint1.toString();
        String actual = constraintCollection.getConstraintForPredicate("zip").toString();
        assertEquals(expected, actual);

        expected = constraint2.toString();
        actual = constraintCollection.getConstraintForPredicate("name").toString();
        assertEquals(expected, actual);

    }

}