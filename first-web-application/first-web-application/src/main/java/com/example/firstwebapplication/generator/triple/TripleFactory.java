package com.example.firstwebapplication.generator.triple;

import com.example.firstwebapplication.annotations.Note;
import com.example.firstwebapplication.generator.constraints.Constraint;
import com.example.firstwebapplication.generator.constraints.ConstraintCollection;
import com.example.firstwebapplication.generator.constraints.Mutation;
import com.example.firstwebapplication.generator.utilities.Utilities;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetRewindable;

import java.util.LinkedList;
import java.util.List;


public class TripleFactory {

    private ResultSetRewindable resultSet;
    private ConstraintCollection constraintCollection;


    public TripleFactory(ResultSet resultSet, ConstraintCollection constraintCollection) {
        this.resultSet = ResultSetFactory.copyResults(resultSet);
        this.constraintCollection = constraintCollection;
    }


    @Note(message = "Set subject names as literal or preserve the URI?")
    public List<Triple> createListOfTriples(
            int totalNumberOfNeededSubject,
            List<String> distinctPredicates,
            String[][] predicateNames) {

        List<Triple> triples = new LinkedList<>();
        QuerySolution solution = null;
        String subjectName = "", objectValue = "";
        int predicateIndex = 0, subjectIndex = 0;
        boolean isAddingNewTriple = true, isObjectALiteral = false;
        final int ORIGINAL_PREDICATE_NAME_INDEX = 0;
        final int ONTOLOGY_PREDICATE_NAME_INDEX = 1;

        while (resultSet.hasNext()) {
            solution = resultSet.next();

            /*
             * increment subject count when previous subject includes ALL necessary predicates
             * and
             * stop when subject count hits the desired limit
             */
            if (predicateIndex == 0 && isAddingNewTriple) {
                if(++subjectIndex > totalNumberOfNeededSubject) {
                    break;
                }
//                else {
//                    subjectName = solution.get("subject").toString();
//                    System.out.println(">> " + subjectName);
//                }
            }

            String predicate = predicateNames[predicateIndex][ORIGINAL_PREDICATE_NAME_INDEX];
            subjectName = "Subject" + subjectIndex;


            /*
             * if the predicate exists,
             *      get the object value of the predicate
             * if the predicate does not exist but is defined by the user,
             *      then add default object value
             * if predicate does not exist and is not defined by the user,
             *      then the user is not interested in the current predicate,
             *      thus just ignore and check the next one
             */
            if (solution.get("predicate").toString().endsWith(predicate)) {
                objectValue = solution.get("object").toString();
                isObjectALiteral = !Utilities.isStringURI(objectValue);
            }
            else {
                if(!distinctPredicates.contains(predicate)) {
                    objectValue = "Object value for some random, non-existing predicate in the original model.";
                }
                else {
                    isAddingNewTriple = false;
                    rewindResultSetIfNeedMoreData(triples, totalNumberOfNeededSubject, predicateNames.length);
                    continue;
                }
            }

            // modify object value if needed
            if(isObjectALiteral) {
                if(Utilities.doesStringContainURI(objectValue)) {
                    objectValue = Utilities.extractURIFromString(objectValue);
                }

                objectValue = mutateObjectIfRequiredByPredicate(
                        predicate, objectValue, constraintCollection
                );
            }

            // map predicate name with ontology if needed
            predicate = mapPredicateWithOntology(
                    predicate,
                    predicateNames[predicateIndex][ONTOLOGY_PREDICATE_NAME_INDEX]
            );

            triples.add(new Triple(subjectName, predicate, objectValue));
            predicateIndex = (predicateIndex + 1) % predicateNames.length;
            isAddingNewTriple = true;
            rewindResultSetIfNeedMoreData(triples, totalNumberOfNeededSubject, predicateNames.length);

        }

        return triples;
    }


    static String mapPredicateWithOntology(String currentPredicateName, String ontologyPredicateName) {
        if(!ontologyPredicateName.isEmpty()) {
            return ontologyPredicateName;
        }
        return currentPredicateName;
    }


    static String mutateObjectIfRequiredByPredicate(
            String predicate,
            String objectValue,
            ConstraintCollection constraintCollection) {

        if(constraintCollection != null) {
            Constraint constraintForCurrentPredicate =
                    constraintCollection.getConstraintForPredicate(predicate);

            if(constraintForCurrentPredicate != null) {
                String originalObjectValue = objectValue;

                constraintForCurrentPredicate.appendMutationParameter(originalObjectValue);
                objectValue = Mutation.mutate(
                        constraintForCurrentPredicate.getMutationOption(),
                        constraintForCurrentPredicate.getMutationParameters()
                );

                constraintForCurrentPredicate.removeMutationParameter(originalObjectValue);

            }
        }
        return objectValue;
    }


    private void printSolution(QuerySolution solution) {
        System.out.println();
        System.out.println(">> Subject = " + solution.get("subject").toString());
        System.out.println(">> Predicate = " + solution.get("predicate").toString());
        System.out.println(">> Object = " + solution.get("object").toString());
        System.out.println();
    }


    private void rewindResultSetIfNeedMoreData(
            List<Triple> triples,
            int totalNumberOfNeededSubject,
            int totalNumberOfPredicatesInEachSubject) {

        boolean isEndOfResultSet = !this.resultSet.hasNext();
        int expectedNumberOfTriples = totalNumberOfNeededSubject * totalNumberOfPredicatesInEachSubject;
        boolean hasEnoughTriples = triples.size() == expectedNumberOfTriples;

        if(isEndOfResultSet && !hasEnoughTriples) {
            resultSet.reset();
        }

    }


}
