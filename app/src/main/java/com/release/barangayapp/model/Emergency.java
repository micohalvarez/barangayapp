package com.release.barangayapp.model;

public class Emergency {

    private String title;
    private String message;
    private String userId;
    private String key;
    private String Phonenumber;
    private int type;
    private boolean isFinished;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhonenumber() { return Phonenumber; }

    public void setPhonenumber(String phonenumber) { this.Phonenumber = phonenumber; }
}
