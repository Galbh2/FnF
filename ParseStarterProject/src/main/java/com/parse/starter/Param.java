package com.parse.starter;

/**
 *This class represents one parameter from the ProfileActivity page of a Place.
 */
public class Param {

    private int type; //0 for yes/no question, 1 for numeric question
    private String body;
    private boolean boolData;
    private Double numData;

    /**
     * Constructor for numeric questions
     * @param body
     * @param numData
     */

    public Param(){

    }

    public Param (String body, double numData) {

        this.type = 1;
        this.body = body;
        this.numData = numData;
    }

    /**
     * Constructor for boolean questions
     * @param body
     * @param boolDate
     */
    public Param (String body, boolean boolDate) {

        this.type = 0;
        this.body = body;
        this.boolData = boolDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean getBoolData() {
        return boolData;
    }

    public void setBoolData(boolean boolDate) {
        this.boolData = boolDate;
    }

    public Double getNumData() {
        return numData;
    }

    public void setNumData(Double numData) {
        this.numData = numData;
    }

    public int getType() {
        return type;
    }



}
