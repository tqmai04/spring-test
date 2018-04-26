package com.example.firstwebapplication.generator.rdf;

import com.example.firstwebapplication.annotations.Note;
import com.example.firstwebapplication.configuration.DataSource;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.resultset.ResultSetCompare;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class SparqlQueryExecutorTest {

    private final String SELECT_ALL_QUERY = SparqlQueryPreparer.createSelectAllQuery();

    private static SparqlQueryExecutor executor;

    /*
     * assume the followings are available on Fuseki server:
     *      Dataset Name                    RDF File Name
     *      -----------------------------------------------------------
     *      AddressesShortNoSubject         AddressesShortNoSubject.ttl
     *      SinglePredicate                 SinglePredicateRDF.ttl
     *      Empty                           Empty.ttl
     *
     * see src/test/resources/sample-json-RDF for these files
     */

    @BeforeAll
    static void init() {
        executor = new SparqlQueryExecutor(new DataSource("test"));
    }


    @BeforeEach
    void setUp() {
    }


    @AfterEach
    void tearDown() {
    }


    @Note(message = "Manually verify they are all the same. But somehow JUnit throws error?")
    @Test
    void testGetDataFromLocalAndFusekiAndURI() {
        String graphName = "AddressesShortNoSubject";

        Model localModel = executor.getModelFromLocal(graphName);
        ResultSet local = executor.getResultSetFromModel(localModel, SELECT_ALL_QUERY);

        Model fusekiModel = executor.getModelFromFusekiServer(graphName);
        ResultSet remote = executor.getResultSetFromModel(fusekiModel, SELECT_ALL_QUERY);
//        ResultSet uri = SparqlQueryExecutor.retrieveDataFromURI
//                ("http://localhost:3030/AddressesShortNoSubject/sparql", SELECT_ALL_QUERY);

//        System.out.println("URI");
//        Utilities.printResultSet(uri);

        assertTrue(ResultSetCompare.equalsByTerm(local, remote));
//        assertTrue(ResultSetCompare.equalsByTerm(local, uri));
    }


    @Test
    void testGetAllDistinctPredicatesFromModelMultiplePredicates() {
        String[] expectedPredicates = {
                "address_no_complete",
                "city",
                "council_person",
                "district_num",
                "full_address",
                "lot_location",
                "st_name",
                "st_suffix_type",
                "zip",
                "something",
                "dob"
        };
        String graphName = "AddressesShortNoSubject";
        Model model = executor.getModelFromFusekiServer(graphName);
        List<String> actualPredicates = executor.getAllDistinctPredicatesFromModel(model);

        for(String expectedPredicate : expectedPredicates) {
            assertTrue(actualPredicates.contains(expectedPredicate));
            actualPredicates.remove(expectedPredicate);
        }

        assertEquals(0, actualPredicates.size());

    }


    @Test
    void testUploadGraphToFuseki() {
        SparqlQueryExecutor executor = new SparqlQueryExecutor(new DataSource("test"));
        List<String> graphNames = executor.getAllDistinctGraphsFromDataset();
        int originalSize = graphNames.size();
        String newGraphName = String.valueOf(UUID.randomUUID());
        Model localModel = executor.getModelFromLocal("Addresses");
        executor.putModelToFuseki(localModel, newGraphName);
        graphNames = executor.getAllDistinctGraphsFromDataset();

        assertEquals(originalSize + 1, graphNames.size());
        assertTrue(graphNames.contains(newGraphName));
    }


    @Test
    void testGetAllDistinctPredicatesFromModelSinglePredicate() {
        String graphName = "SinglePredicate";
        Model model = executor.getModelFromFusekiServer(graphName);
        List<String> actualPredicates = executor.getAllDistinctPredicatesFromModel(model);
        assertTrue(actualPredicates.contains("city"));
        actualPredicates.remove("city");
        assertEquals(0, actualPredicates.size());

    }


    @Test
    void testGetAllDistinctPredicatesFromModelEmpty() {
        String graphName = "Empty";
        Model model = executor.getModelFromFusekiServer(graphName);
        List<String> actualPredicates = executor.getAllDistinctPredicatesFromModel(model);
        assertTrue(actualPredicates.isEmpty());

    }


}