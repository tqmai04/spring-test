package com.example.firstwebapplication.generator.rdf;

import com.example.firstwebapplication.generator.triple.Triple;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


class RDFGeneratorTest {

    private Model model;


    @BeforeEach
    void setup() {
        model = RDFGenerator.createDefaultModel();
    }


    @Test
    void testAddTripleToModel() {
        Query query = QueryFactory.create(SparqlQueryPreparer.createCountAllQuery());
        QueryExecution qExe = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qExe.execSelect();
        QuerySolution solution = resultSet.next();
        assertTrue(solution.get("triples").toString().startsWith("0"));

        Triple triple = new Triple("Address1", "zip",  "75090");
        model = RDFGenerator.addTripleToModel(model, triple);
        qExe = QueryExecutionFactory.create(query, model);
        resultSet = qExe.execSelect();
        solution = resultSet.next();
        assertTrue(solution.get("triples").toString().startsWith("1"));
    }


    @Test
    void testAddMultipleTriplesToModel() {
        Query query = QueryFactory.create(SparqlQueryPreparer.createCountAllQuery());
        QueryExecution qExe = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qExe.execSelect();
        QuerySolution solution = resultSet.next();
        assertTrue(solution.get("triples").toString().startsWith("0"));

        List<Triple> triples = new LinkedList<>();
        triples.add(new Triple("Address1", "city", "Richardson"));
        triples.add(new Triple("Address1", "state", "TX"));
        triples.add(new Triple("Address2", "zip",  "XHSBS"));
        triples.add(new Triple("Address2", "city", "Random"));
        triples.add(new Triple("Address2", "state", "UNKNOWN"));
        model = RDFGenerator.addMultipleTriplesToModel(model, triples);
        qExe = QueryExecutionFactory.create(query, model);
        resultSet = qExe.execSelect();
        solution = resultSet.next();
        assertTrue(solution.get("triples").toString().startsWith("5"));

    }
}