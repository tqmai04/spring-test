package com.example.firstwebapplication.generator.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class JSONRequest {

    private String request;
    private String type;
    private String name;
    private String limit;
    private LinkedList<String[]> predicates;
    private LinkedList<String[]> constraints;


    public JSONRequest() {
        this.request = "";
        this.type = "";
        this.name = "";
        this.limit = "";
        this.predicates = new LinkedList<>();
        this.constraints = new LinkedList<>();
    }


    public void initialize(String jsonString) throws JSONException {
        JSONObject request = new JSONObject(jsonString);

        setRequest(request);
        setType(request);
        setName(request);
        setLimit(request);
        setPredicates(request);
        setConstraints(request);
    }


    private void setRequest(JSONObject request) throws JSONException {
        if(request.has("request")) {
            this.request = request.getString("request");
        }
        else {
            this.request = null;
        }
    }


    private void setType(JSONObject request) throws JSONException {
        if(request.has("type")) {
            this.type = request.getString("type");
        }
        else {
            this.type = null;
        }
    }


    private void setName(JSONObject request) throws JSONException {
        if(request.has("name")) {
            this.name = request.getString("name");
        }
        else {
            this.name = null;
        }
    }


    private void setLimit(JSONObject request) throws JSONException {
        if(request.has("limit")) {
            this.limit = request.getString("limit");
        }
        else {
            this.limit = null;
        }
    }


    private void setPredicates(JSONObject request) throws JSONException {
        if(request.has("predicates")) {
            JSONArray allPredicatesJsonArray = request.getJSONArray("predicates");
            for(int i = 0; i < allPredicatesJsonArray.length(); i++) {
                JSONArray currentPredicatesJson = allPredicatesJsonArray.getJSONArray(i);
                String[] currentPredicates = new String[currentPredicatesJson.length()];

                for(int j = 0; j < currentPredicatesJson.length(); j++) {
                    currentPredicates[j] = (String)currentPredicatesJson.get(j);
                }

                this.predicates.addLast(currentPredicates);
            }
        }
        else {
            this.predicates = null;
        }
    }


    private void setConstraints(JSONObject request) throws JSONException {
        if(request.has("constraints")) {
            JSONArray allConstraintsJsonArray = request.getJSONArray("constraints");
            for(int i = 0; i < allConstraintsJsonArray.length(); i++) {
                JSONArray currentConstraintsJson = allConstraintsJsonArray.getJSONArray(i);
                String[] currentConstraints = new String[currentConstraintsJson.length()];

                for(int j = 0; j < currentConstraintsJson.length(); j++) {
                    currentConstraints[j] = (String)currentConstraintsJson.get(j);
                }

                this.constraints.addLast(currentConstraints);
            }
        }
        else {
            this.constraints = null;
        }
    }


    public String getRequest() {
        return request;
    }


    public String getType() {
        return type;
    }


    public String getName() {
        return name;
    }


    public String getLimit() {
        return limit;
    }


    public String[][] getPredicates() {
        if(this.predicates == null) {
            return null;
        }

        String[][] predicateArray = new String[this.predicates.size()][2];
        int currentPairIndex = 0;

        for(String[] eachPair : this.predicates) {
            System.arraycopy(
                    eachPair, 0,
                    predicateArray[currentPairIndex], 0,
                    eachPair.length
            );
            currentPairIndex++;
        }
        return predicateArray;
    }


    public String[][] getConstraints() {
        if(this.constraints == null) {
            return null;
        }

        int totalNumberOfConstraints = this.constraints.size();
        String[][] constraintsArray = new String[totalNumberOfConstraints][5];

        for(int currentConstraintIndex = 0;
            currentConstraintIndex < totalNumberOfConstraints;
            currentConstraintIndex++) {

            String[] currentConstraintParameters =
                    this.constraints.get(currentConstraintIndex);
            constraintsArray[currentConstraintIndex] =
                    new String[currentConstraintParameters.length];

            System.arraycopy(
                    currentConstraintParameters, 0,
                    constraintsArray[currentConstraintIndex], 0,
                    currentConstraintParameters.length);

        }

        return constraintsArray;
    }

}
