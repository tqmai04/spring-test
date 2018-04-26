package com.example.firstwebapplication.store;


import com.example.firstwebapplication.configuration.DataSource;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;


@Deprecated
public class RuntimeStore {

    private DataSource dataSource;
    public RDFConnection connection;


    public RuntimeStore(DataSource dataSource) {

        this.dataSource = dataSource;
        this.connection = RDFConnectionFactory.connect(dataSource.getSparqlQueryEndpoint(),
                dataSource.getSparqlUpdateEndpoint(),
                dataSource.getGraphStoreProtocolEndpoint()
        );
    }

    public Model getGraph(String graphIri) {

        String sparql = String.format("CONSTRUCT { ?s ?p ?o } WHERE {{ GRAPH <%s> {{ ?s ?p ?o }} }}", graphIri);

        QueryExecution qExec = connection.query(sparql);

        Model model = qExec.execConstruct();

        qExec.close();

        return model;
    }

    public void saveGraph(Model model, Resource subjectNode) {
        try {
            connection.put(subjectNode.toString(), model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
