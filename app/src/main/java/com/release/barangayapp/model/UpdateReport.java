package com.release.barangayapp.model;

public class UpdateReport {


    private String deaths;
    private String recovered;
    private String confirmed;

    public String getUpdateDateView() {
        return updateDateView;
    }

    public void setUpdateDateView(String updateDateView) {
        this.updateDateView = updateDateView;
    }

    private String updateDateView;

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }


}
