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
public class RealGraphWithLinkedDataTest {

    private static DataSource dataSource = new DataSource("test");
    private static final String LIMIT = "3";
    private static final String[][] PREDICATES = {
            {
                    // literal
                    "color",
                    ""
            },
            {
                    // uri
                    "wikiPageExternalLink",
                    ""
            }
    };
    private static final String[][] CONSTRAINTS = {
            {
                    // shorten string literal
                    "color", Integer.toString(Mutation.TRUNCATE_NUMBER_OF_CHARACTERS), "1"
            },
            {
                    // change object uri (expected not to be changed)
                    "wikiPageExternalLink", Integer.toString(Mutation.CREATE_RANDOM_STRING), "10"
            }
    };
    private static final String SELECT_ALL_QUERY = SparqlQueryPreparer.createSelectAllQuery();


    public static void main(String[] args) {
        SparqlQueryExecutor executor = new SparqlQueryExecutor(dataSource);
        Model model = executor.getModelFromFusekiServer("RealData");
        ResultSet resultSet = executor.getResultSetFromModel(model, SELECT_ALL_QUERY);


        ConstraintCollection constraintCollection = ConstraintCollectionFactory
                .createConstraintCollection(CONSTRAINTS);
        TripleFactory tripleFactory = new TripleFactory(resultSet, constraintCollection);
        List<String> distinctPredicates =
                executor.getAllDistinctPredicatesFromModel(model);

        List<Triple> modifiedTriples = tripleFactory.createListOfTriples(
                Integer.parseInt(LIMIT), distinctPredicates, PREDICATES);
        Model mode = RDFGenerator.createDefaultModel();
        mode = RDFGenerator.addMultipleTriplesToModel(mode, modifiedTriples);

        // make sure that wikiPageExternalLink still maintains its URI object
        executor.putModelToLocalRdfGraph(mode, "ModelWithURIObject.ttl");

    }


}
