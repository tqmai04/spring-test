package com.example.firstwebapplication.configuration;

import com.example.firstwebapplication.store.RuntimeStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class DataSource {

    private String sparqlQueryEndpoint;
    private String sparqlUpdateEndpoint;
    private String graphStoreProtocolEndpoint;


    public DataSource() {
        /*
         * default
         * see main/resources/application.properties for actual endpoints
         */
        sparqlQueryEndpoint = "http://localhost:3030/self_service/sparql";
        sparqlUpdateEndpoint = "http://localhost:3030/self_service/update";
        graphStoreProtocolEndpoint = "http://localhost:3030/self_service/data";
    }
    
    
    public DataSource(String name) {
        sparqlQueryEndpoint = String.format("http://localhost:3030/%s/sparql", name);
        sparqlUpdateEndpoint = String.format("http://localhost:3030/%s/update", name);
        graphStoreProtocolEndpoint = String.format("http://localhost:3030/%s/data", name);
    }


    public String getSparqlUpdateEndpoint() {
        return sparqlUpdateEndpoint;
    }

    public void setSparqlUpdateEndpoint(String sparqlUpdateEndpoint) {
        this.sparqlUpdateEndpoint = sparqlUpdateEndpoint;
    }

    @Bean
    public RuntimeStore getRuntimeStore() {
        return new RuntimeStore(this);
    }

    public String getGraphStoreProtocolEndpoint() {
        return graphStoreProtocolEndpoint;
    }

    public void setGraphStoreProtocolEndpoint(String graphStoreProtocolEndpoint) {
        this.graphStoreProtocolEndpoint = graphStoreProtocolEndpoint;
    }

    public String getSparqlQueryEndpoint() {
        return sparqlQueryEndpoint;
    }

    public void setSparqlQueryEndpoint(String sparqlQueryEndpoint) {
        this.sparqlQueryEndpoint = sparqlQueryEndpoint;
    }
}
