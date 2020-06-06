package com.example.foodforyou.model;

// Request API
public class RecommendDietList {
    // required
    String apiKey;
    String dietSeCode;

    //optional
    int pageNo;
    int numOfRows;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDietSeCode() {
        return dietSeCode;
    }

    public void setDietSeCode(String dietSeCode) {
        this.dietSeCode = dietSeCode;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }
}
