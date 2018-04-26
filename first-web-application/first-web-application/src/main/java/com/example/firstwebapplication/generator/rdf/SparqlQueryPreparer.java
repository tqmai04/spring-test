package com.example.firstwebapplication.generator.rdf;

public class SparqlQueryPreparer {

    public static String createSelectAllQuery() {

        return  "SELECT ?subject ?predicate ?object\n" +
                "WHERE {\n" +
                "  ?subject ?predicate ?object\n" +
                "}\n";

    }


    public static String createConstructQuery(String queryEndPointURI, String graphName) {
        if(!queryEndPointURI.endsWith("/")) {
            queryEndPointURI += "/";
        }

        return String.format(
                "CONSTRUCT { ?s ?p ?o } \n" +
                "WHERE {\n" +
                "   {\n" +
                "       GRAPH <%s%s> {\n" +
                "           ?s ?p ?o \n" +
                "       } \n" +
                "   }\n" +
                "}",
                queryEndPointURI,
                graphName
        );
    }


    public static String createCountAllQuery() {
        return "SELECT (COUNT(?s) AS ?triples) WHERE {\n" +
                "    ?s ?p ?o\n" +
                "}";
    }


    public static String createSelectAllDistinctPredicatesQuery() {

        return "select distinct ?predicate\n" +
                "where {\n" +
                "   ?s ?predicate ?o\n" +
                "}";

    }


    public static String createSelectAllGraphNameQuery() {
        return "SELECT DISTINCT ?g \n" +
                "WHERE {\n" +
                "  GRAPH ?g { ?s ?p ?o }\n" +
                "}";
    }

}
