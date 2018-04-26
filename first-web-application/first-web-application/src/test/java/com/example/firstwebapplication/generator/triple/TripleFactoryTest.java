package com.example.firstwebapplication.generator.triple;

import com.example.firstwebapplication.generator.constraints.Constraint;
import com.example.firstwebapplication.generator.constraints.ConstraintCollection;
import com.example.firstwebapplication.generator.constraints.Mutation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TripleFactoryTest {

    private final Constraint TRUNCATE_ZIP_CONSTRAINT = new Constraint(
            "zip", Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "1");

    private final Constraint REPLACE_CHARACTER_COUNCIL_PERSON_CONSTRAINT = new Constraint(
            "council_person", Mutation.REPLACE_CHARACTER, "H", "A");



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
    void testMutateObjectIfRequiredByPredicateNoModify() {
        String predicate = TRUNCATE_ZIP_CONSTRAINT.getPredicate();
        String objectValue = "75074";
        String expectedValue = "75074";
        String actualValue = TripleFactory.mutateObjectIfRequiredByPredicate(
                predicate, objectValue, null
        );
        assertEquals(expectedValue, actualValue);

        actualValue = TripleFactory.mutateObjectIfRequiredByPredicate(
                predicate, objectValue, constraintCollection
        );
        assertEquals(expectedValue, actualValue);
    }


    @Test
    void testMutateObjectIfRequiredByPredicateSingleTriple() {
        String predicate = TRUNCATE_ZIP_CONSTRAINT.getPredicate();
        constraintCollection.addConstraint(TRUNCATE_ZIP_CONSTRAINT);
        String objectValue = "75074";
        String expectedModifiedValue = "7507";
        String actualModifiedValue = TripleFactory.mutateObjectIfRequiredByPredicate(
                predicate, objectValue, constraintCollection
        );
        assertEquals(expectedModifiedValue, actualModifiedValue);

    }


    @Test
    void testMutateObjectIfRequiredByPredicateMultipleTriplesSameRule() {
        String predicate = REPLACE_CHARACTER_COUNCIL_PERSON_CONSTRAINT.getPredicate();
        constraintCollection.addConstraint(REPLACE_CHARACTER_COUNCIL_PERSON_CONSTRAINT);

        // modify first triple
        String objectValue1 = "Hh Ww";
        String expectedModifiedValue1 = "Ah Ww";
        String actualModifiedValue1 = TripleFactory.mutateObjectIfRequiredByPredicate(
                predicate, objectValue1, constraintCollection
        );
        assertEquals(expectedModifiedValue1, actualModifiedValue1);

        // modify second triple with the same rule
        String objectValue2 = "H";
        String expectedModifiedValue2 = "A";
        String actualModifiedValue2 = TripleFactory.mutateObjectIfRequiredByPredicate(
                predicate, objectValue2, constraintCollection
        );
        assertEquals(expectedModifiedValue2, actualModifiedValue2);
        assertNotEquals(expectedModifiedValue2, actualModifiedValue1);

    }


    @Test
    void testCreateListOfTriplesWithoutMappingToOntology() {
        String[][] predicates = {
                {
                        "zip",
                        ""
                },
                {
                        "council_person",
                        ""
                },
                {
                        "district_num",
                        ""
                }
        };

        for(String[] currentPair : predicates) {
            String expectedPredicateName = currentPair[0];
            String actualPredicateName = TripleFactory.mapPredicateWithOntology(
                    currentPair[0], currentPair[1]
            );
            assertEquals(expectedPredicateName, actualPredicateName);
        }

    }


    @Test
    void testCreateListOfTriplesWithMappingToOntology() {
        String[][] predicates = {
                {
                        "zip",
                        "zip_code"
                },
                {
                        "council_person",
                        "full_name"
                },
                {
                        "district_num",
                        "district_number"
                }
        };

        for(String[] currentPair : predicates) {
            String expectedPredicateName = currentPair[1];
            String actualPredicateName = TripleFactory.mapPredicateWithOntology(
                    currentPair[0], currentPair[1]
            );
            assertEquals(expectedPredicateName, actualPredicateName);
        }

    }

}