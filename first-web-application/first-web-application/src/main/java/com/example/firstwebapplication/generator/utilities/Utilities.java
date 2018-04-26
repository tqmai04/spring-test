package com.example.firstwebapplication.generator.utilities;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import java.util.List;


public class Utilities {

    public static void printListOfStrings(List<String> strings) {
        System.out.println();
        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println();
    }


    public static boolean isStringURI(String s) {
        String urlRegex = "^(http|https)://\\S+.(com|org|net|edu|gov)\\S+$";
        String localhostRegex = "^(http|https)://localhost:\\d+/\\S+$";

        return s.matches(urlRegex) ||
                s.matches(localhostRegex);
    }


    public static boolean doesStringContainURI(String s) {
        String urlRegex = "\\S+(http|https)://\\S+.(com|org|net|edu|gov)\\S+";
        String localhostRegex = "\\S+(http|https)://localhost:\\d+/\\S+";

        return s.matches(urlRegex) ||
                s.matches(localhostRegex);
    }


    /*
     * Target some data from dbpedia, such as:
     *      "0.572"^^<http://www.w3.org/2001/XMLSchema#double>
     */
    public static String extractURIFromString(String s) {
        int endIndex = 0;
        String subString = "";

        // uri part
        if(s.contains("localhost")) {
            endIndex = s.indexOf("localhost");
        }
        if(s.contains("http")) {
            endIndex = s.indexOf("http");
        }
        if(s.charAt(endIndex - 1) == '<') {
            endIndex--;
        }
        subString = s.substring(0, endIndex);

        // ^^ marker
        if(subString.contains("^^")) {
            endIndex = s.indexOf("^^");
        }
        subString = s.substring(0, endIndex);

        // wrap inside quote
        if(subString.startsWith("\"") && subString.endsWith("\"")) {
            subString = subString.substring(1, subString.length() - 1);
        }

        return subString;
    }


    public static void printResultSet(ResultSet resultSet) {
        ResultSetFormatter.out(System.out, resultSet);
    }



}
