package com.example.firstwebapplication.generator.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonResponse {

    private JSONObject response;


    public JsonResponse() {
        response = new JSONObject();
    }


    public void addResponse(String response) throws JSONException {
        this.response.put("response", response);
    }


    public void addSingleDataLine(String data) throws JSONException {
        response.put("data", data);
    }


    public void addArrayOfDataLines(String[] data) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for(String datum : data) {
            jsonArray.put(datum);
        }
        response.put("data", jsonArray);
    }


    public void add2dArrayOfDataLines(String[][] data) throws JSONException {
        JSONArray firstLevel = new JSONArray();

        for(String[] eachRow : data) {
            JSONArray secondLevel = new JSONArray();

            for(String eachCol : eachRow) {
                secondLevel.put(eachCol);
            }

            firstLevel.put(secondLevel);

        }

        this.response.put("data", firstLevel);

    }


    public void addJSONObject(JSONObject jsonObject) throws JSONException {
        this.response.put("data", jsonObject);
    }


    public String toString() {
        return response.toString();
    }


}
