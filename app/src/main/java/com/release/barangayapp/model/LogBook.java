package com.release.barangayapp.model;

import java.util.ArrayList;

public class LogBook {

    private ArrayList<Integer> symptoms;
    private ArrayList<Integer> otherSymptoms;
    private String userId;

    public ArrayList<Integer> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<Integer> symptoms) {
        this.symptoms = symptoms;
    }

    public ArrayList<Integer> getOtherSymptoms() {
        return otherSymptoms;
    }

    public void setOtherSymptoms(ArrayList<Integer> otherSymptoms) {
        this.otherSymptoms = otherSymptoms;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
