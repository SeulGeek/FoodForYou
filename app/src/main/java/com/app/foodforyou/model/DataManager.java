package com.app.foodforyou.model;

import java.util.ArrayList;

public class DataManager {

    ArrayList<DietListResponse> dietListResponses = new ArrayList<>();

    public ArrayList<DietListResponse> getDietListResponses() {
        return dietListResponses;
    }

    public void setDietListResponses(ArrayList<DietListResponse> dietListResponses) {
        this.dietListResponses = dietListResponses;
    }

    public DietListResponse getLastDietListData() {
        return dietListResponses.get(dietListResponses.size() - 1);
    }

}
