package com.release.barangayapp.model;

public class UpdateReport {

    private int probable;
    private int suspect;
    private int confirmed;

    public int getProbable() {
        return probable;
    }

    public void setProbable(int probable) {
        this.probable = probable;
    }

    public int getSuspect() {
        return suspect;
    }

    public void setSuspect(int suspect) {
        this.suspect = suspect;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
