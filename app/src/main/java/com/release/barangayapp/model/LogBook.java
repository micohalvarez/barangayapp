package com.release.barangayapp.model;

import java.util.ArrayList;

public class LogBook {

    private ArrayList<Integer> symptoms;
    private ArrayList<Integer> otherSymptoms;
    private ArrayList<Integer> healthChecklist;
    private String userId;
    private String surveyDate;

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }



    public LogBook(){
        symptoms = new ArrayList<>();
        otherSymptoms = new ArrayList<>();
        healthChecklist = new ArrayList<>();

    }
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

    public ArrayList<Integer> getHealthChecklist() {
        return healthChecklist;
    }

    public void setHealthChecklist(ArrayList<Integer> healthChecklist) {
        this.healthChecklist = healthChecklist;
    }
}