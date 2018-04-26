package com.example.firstwebapplication.generator.rdf;

import com.example.firstwebapplication.configuration.DataSource;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class SparqlQueryExecutor {

    private RDFConnection connection;
    private DataSource dataSource;


    public SparqlQueryExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
        this.connection = RDFConnectionFactory.connect(
                dataSource.getSparqlQueryEndpoint(),
                dataSource.getSparqlUpdateEndpoint(),
                dataSource.getGraphStoreProtocolEndpoint()
        );

    }


    public Model getModelFromFusekiServer(String graphName) {
        String sparql = SparqlQueryPreparer.createConstructQuery(
                dataSource.getGraphStoreProtocolEndpoint(), graphName);
        QueryExecution qExec = connection.query(sparql);
        return qExec.execConstruct();
    }


    public Model getModelFromURI(String uri) {
        Model model = ModelFactory.createDefaultModel();
        model.read(uri);
        return model;
    }


    public Model getModelFromLocal(String rdfFileName) {
        Model model = ModelFactory.createDefaultModel();
        model.read(String.format("sample-rdf/%s.ttl", rdfFileName));
        return model;
    }


    public ResultSet getResultSetFromModel(Model model, String selectQuery) {
        Query query = QueryFactory.create(selectQuery);
        QueryExecution qExe = QueryExecutionFactory.create(query, model);
        return qExe.execSelect();
    }


    public void putModelToFuseki(Model model, String graphName) {
        try {
            connection.put(graphName, model);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void putModelToLocalRdfGraph(Model model, String fileName) {

        try {
            File outputFile = new File("src/test/resources/sample-rdf/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            if(!outputFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.createNewFile();
            }

            RDFDataMgr.write(fileOutputStream, model,
                    RDFFormat.TTL);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<String> getAllDistinctGraphsFromDataset() {
        String query = SparqlQueryPreparer.createSelectAllGraphNameQuery();
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(
                dataSource.getSparqlQueryEndpoint(), query);
        ResultSet resultSet = queryExecution.execSelect();
        List<String> distinctGraphNames = new ArrayList<>();

        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            String graphName = solution.get("g").toString();
            distinctGraphNames.add(graphName.substring(graphName.lastIndexOf("/") + 1));
        }

        return distinctGraphNames;
    }


    public List<String> getAllDistinctPredicatesFromModel(Model model) {
        String query = SparqlQueryPreparer.createSelectAllDistinctPredicatesQuery();
        ResultSet resultSet = getResultSetFromModel(model, query);
        List<String> distinctPredicates = new ArrayList<>();

        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            String predicate = solution.get("predicate").toString();

            if(!predicate.contains("#")) {
                distinctPredicates.add(predicate.substring(predicate.lastIndexOf("/") + 1));
            }

        }

        return distinctPredicates;

    }

}
