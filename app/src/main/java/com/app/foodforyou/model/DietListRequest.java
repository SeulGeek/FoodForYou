package com.app.foodforyou.model;

// Request API
public class DietListRequest {
    // required
    private String apiKey; // 발급받은 Open API 인증키 (issued open API auth key)
    private String dietSeCode; // 식단 구분코드 (Separation code of diet)

    //optional
    private int pageNo; // 조회할 페이지 번호 (page number to search(inquiry))
    private int numOfRows; // 한 페이지에 제공할 건수 (Number of cases to offer per page)

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
