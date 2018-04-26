package com.example.firstwebapplication.generator.console;

import com.example.firstwebapplication.configuration.DataSource;
import com.example.firstwebapplication.generator.constraints.ConstraintCollection;
import com.example.firstwebapplication.generator.constraints.ConstraintCollectionFactory;
import com.example.firstwebapplication.generator.constraints.Mutation;
import com.example.firstwebapplication.generator.rdf.RDFGenerator;
import com.example.firstwebapplication.generator.rdf.SparqlQueryExecutor;
import com.example.firstwebapplication.generator.rdf.SparqlQueryPreparer;
import com.example.firstwebapplication.generator.triple.Triple;
import com.example.firstwebapplication.generator.triple.TripleFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.junit.jupiter.api.Disabled;

import java.util.List;


/***
 * @see com.example.firstwebapplication.generator.constraints.Mutation for options and their parameters
 */
@Disabled
public class IntegrationTest {

    private static final String GRAPH_NAME = "Addresses";
    private static final String LIMIT = "3";
    private static final String[][] EXISTING_PREDICATES = {
            {
                    "council_person",
                    "full_name"
            },
            {
                    "full_address",
                    "address"
            },
            {
                    "city",
                    ""
            },
            {
                    "district_num",
                    ""
            },
            {
                    "zip",
                    "zip_code"
            }
    };
    private static final String[][] NONEXISTING_PREDICATES = {
            {
                    "council_person",
                    "full_name"
            },
            {
                    "full_address",
                    "address"
            },
            {
                    "city",
                    ""
            },
            {
                    "district_num",
                    ""
            },
            {
                    "zip",
                    "zip_code"
            },
            {
                    // try to inject nonexisting predicate from the original
                    "random_predicate",
                    ""
            }
    };
    private static final String[][] CONSTRAINTS = {
            {
                    // change characters in name
                    "council_person", Integer.toString(Mutation.REPLACE_CHARACTER), "a", "w"
            },
            {
                    // shorten string
                    "full_address", Integer.toString(Mutation.TRUNCATE_NUMBER_OF_CHARACTERS), "5"
            },
            {
                    // completely irrelevant information
                    "district_num", Integer.toString(Mutation.CREATE_ALWAYS_INVALID_DATES)
            },
            {
                    // number is too big
                    "zip", Integer.toString(Mutation.CREATE_NEGATIVE_OVERFLOW_INTEGER)
            }
    };
    private static DataSource dataSource = new DataSource("test");
    private static final String SELECT_ALL_QUERY = SparqlQueryPreparer.createSelectAllQuery();


    public static void main(String[] args) {
        makeGoodModel();
        makeBadModel();
    }


    private static void makeGoodModel() {
        SparqlQueryExecutor executor = new SparqlQueryExecutor(dataSource);
        Model fusekiModel = executor.getModelFromFusekiServer(GRAPH_NAME);
        ResultSet resultSet = executor.getResultSetFromModel(fusekiModel, SELECT_ALL_QUERY);
        TripleFactory tripleFactory = new TripleFactory(resultSet, null);
        List<String> distinctPredicates = executor.getAllDistinctPredicatesFromModel(fusekiModel);
        List<Triple> triples = tripleFactory.createListOfTriples(
                Integer.parseInt(LIMIT), distinctPredicates, EXISTING_PREDICATES);
        Model goodModel = RDFGenerator.createDefaultModel();
        goodModel = RDFGenerator.addMultipleTriplesToModel(goodModel, triples);
        executor.putModelToLocalRdfGraph(goodModel, "GoodModel.ttl");
        executor.putModelToFuseki(goodModel, "GoodModel");
    }


    private static void makeBadModel() {
        SparqlQueryExecutor executor = new SparqlQueryExecutor(dataSource);
        Model fusekiModel = executor.getModelFromFusekiServer(GRAPH_NAME);
        ResultSet resultSet = executor.getResultSetFromModel(fusekiModel, SELECT_ALL_QUERY);
        ConstraintCollection constraintCollection = ConstraintCollectionFactory
                .createConstraintCollection(CONSTRAINTS);
        TripleFactory tripleFactory = new TripleFactory(resultSet, constraintCollection);
        List<String> distinctPredicates = executor.getAllDistinctPredicatesFromModel(fusekiModel);
        List<Triple> modifiedTriples = tripleFactory.createListOfTriples(
                Integer.parseInt(LIMIT), distinctPredicates, NONEXISTING_PREDICATES);
        Model badModel = RDFGenerator.createDefaultModel();
        badModel = RDFGenerator.addMultipleTriplesToModel(badModel, modifiedTriples);
        executor.putModelToLocalRdfGraph(badModel, "BadModel.ttl");
        executor.putModelToFuseki(badModel, "BadModel");
    }


}
