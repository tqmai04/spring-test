package com.example.firstwebapplication.generator.utilities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JSONRequestTest {

    private JSONRequest request;
    private String jsonString;


    @BeforeEach
    void setUp() {
        request = new JSONRequest();
    }


    @AfterEach
    void tearDown() {
        request = null;
        jsonString = "";
    }


    @Test
    void testSupplyOriginalGraph() {
        try {
            jsonString =
                    "{\n" +
                    "    \"request\": \"send model\",\n" +
                    "    \"type\": \"fuseki\",\n" +
                    "    \"name\": \"fuseki dataset name or uri\"\n" +
                    "}";
            request.initialize(jsonString);

            assertEquals("send model", request.getRequest());
            assertEquals("fuseki", request.getType());
            assertEquals("fuseki dataset name or uri", request.getName());
            assertNull(request.getLimit());
            assertNull(request.getPredicates());
            assertNull(request.getConstraints());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testRequestPredicates() {
        try {
            jsonString =
                    "{\n" +
                    "    \"request\": \"request\",\n" +
                    "    \"type\": \"distinct predicates\"\n" +
                    "}";
            request.initialize(jsonString);

            assertEquals("request", request.getRequest());
            assertEquals("distinct predicates", request.getType());
            assertNull(request.getName());
            assertNull(request.getLimit());
            assertNull(request.getPredicates());
            assertNull(request.getConstraints());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testRequestMutationOptions() {
        try {
            jsonString =
                    "{\n" +
                    "    \"request\": \"request\",\n" +
                    "    \"type\": \"mutation options\"\n" +
                    "}";
            request.initialize(jsonString);

            assertEquals("request", request.getRequest());
            assertEquals("mutation options", request.getType());
            assertNull(request.getName());
            assertNull(request.getLimit());
            assertNull(request.getPredicates());
            assertNull(request.getConstraints());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testRequestParameters() {
        try {
            jsonString =
                    "{\n" +
                    "    \"request\": \"request\",\n" +
                    "    \"type\": \"parameters\",\n" +
                    "    \"name\": \"option\"\n" +
                    "}";
            request.initialize(jsonString);

            assertEquals("request", request.getRequest());
            assertEquals("parameters", request.getType());
            assertEquals("option", request.getName());
            assertNull(request.getLimit());
            assertNull(request.getPredicates());
            assertNull(request.getConstraints());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testRequestGeneratedRdfGraph() {
        try {
            jsonString = "{\n" +
                    "    \"request\": \"generate model\",\n" +
                    "    \"name\": \"Address\",\n" +
                    "    \"limit\": \"5\",\n" +
                    "    \"predicates\": [\n" +
                    "        [\n" +
                    "            \"council_person\",\n" +
                    "            \"full_name\"\n" +
                    "        ],\n" +
                    "        [\n" +
                    "            \"full_address\",\n" +
                    "            \"address\"\n" +
                    "        ],\n" +
                    "        [\n" +
                    "            \"city\",\n" +
                    "            \"\"\n" +
                    "        ],\n" +
                    "        [\n" +
                    "            \"district_num\",\n" +
                    "            \"\"\n" +
                    "        ],\n" +
                    "        [\n" +
                    "            \"zip\",\n" +
                    "            \"zip_code\"\n" +
                    "        ]\n" +
                    "    ],\n" +
                    "    \"constraints\": [\n" +
                    "        [\n" +
                    "            \"council_person\",\n" +
                    "            \"13\",\n" +
                    "            \"a\",\n" +
                    "            \"w\"\n" +
                    "        ],\n" +
                    "        [\n" +
                    "            \"full_address\",\n" +
                    "            \"15\",\n" +
                    "            \"5\"\n" +
                    "        ],\n" +
                    "        [\n" +
                    "            \"district_num\",\n" +
                    "            \"24\"\n" +
                    "        ],\n" +
                    "        [\n" +
                    "            \"zip\",\n" +
                    "            \"2\"\n" +
                    "        ]\n" +
                    "    ]\n" +
                    "}";
            request.initialize(jsonString);

            assertEquals("generate model", request.getRequest());
            assertNull(request.getType());
            assertEquals("Address", request.getName());
            assertEquals("5", request.getLimit());
            verifyPredicates();
            verifyConstraints();
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    private void verifyPredicates() {
        String[][] expected = {
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
        String[][] actual = request.getPredicates();

        assertEquals(expected.length, actual.length);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].length, actual[i].length);
            assertArrayEquals(expected[i], actual[i]);
        }

    }


    private void verifyConstraints() {
        String[][] expected = {
                {
                        "council_person",
                        "13",
                        "a",
                        "w"
                },
                {
                        "full_address",
                        "15",
                        "5"
                },
                {
                        "district_num",
                        "24"
                },
                {
                        "zip",
                        "2"
                }
        };
        String[][] actual = request.getConstraints();

        assertEquals(expected.length, actual.length);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].length, actual[i].length);
            assertArrayEquals(expected[i], actual[i]);
        }
    }

}