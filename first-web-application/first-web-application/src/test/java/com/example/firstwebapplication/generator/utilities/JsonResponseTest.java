package com.example.firstwebapplication.generator.utilities;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JsonResponseTest {

    private JsonResponse response;


    @BeforeEach
    void setUp() {
        response = new JsonResponse();
    }


    @AfterEach
    void tearDown() {
        response = null;
    }


    @Test
    void testAddResponse() {
        try {
            response.addResponse("sample");
            String expected = "{\"response\":\"sample\"}";
            String actual = response.toString();
            assertEquals(expected, actual);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testAddSingleDataLine() {
        try {
            response.addSingleDataLine("something");
            String expected = "{\"data\":\"something\"}";
            String actual = response.toString();
            assertEquals(expected, actual);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testAddArrayOfDataLines() {
        try {
            response.addArrayOfDataLines(new String[]{
                    "item1",
                    "item2"
            });
            String expected = "{\"data\":[\"item1\",\"item2\"]}";
            String actual = response.toString();
            assertEquals(expected, actual);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testAdd2dArrayOfDataLines() {
        try {
            response.add2dArrayOfDataLines(new String[][]{
                    {
                            "item11",
                            "item12"
                    },
                    {
                            "item21",
                            "item22"
                    }
            });
            String expected = "{\"data\":[[\"item11\",\"item12\"],[\"item21\",\"item22\"]]}";
            String actual = response.toString();
            assertEquals(expected, actual);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testAddJSONObject() {
        try {
            String jsonString =
                    "{\n" +
                    "    \"title\":\"json\",\n" +
                    "    \"stuff\":\n" +
                    "    {\n" +
                    "        \"name\":\"subitem\",\n" +
                    "        \"list\":\n" +
                    "        [\n" +
                    "            \"li1\",\n" +
                    "            \"li2\"\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
            JSONObject paramJsonObject = new JSONObject(jsonString);
            response.addJSONObject(paramJsonObject);

            JSONObject wrapperJsonObject = new JSONObject(response.toString());
            assertTrue(wrapperJsonObject.has("data"));
            JSONObject actualParamObject = wrapperJsonObject.getJSONObject("data");
            assertEquals(paramJsonObject.toString(), actualParamObject.toString());

        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

}