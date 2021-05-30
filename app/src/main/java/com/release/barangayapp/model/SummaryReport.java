package com.release.barangayapp.model;

import android.widget.EditText;

public class SummaryReport {

    private String probable;
    private String suspect;
    private String confirmed;

    public String getProbable() {
        return probable;
    }

    public void setProbable(String probable) {
        this.probable = probable;
    }

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
