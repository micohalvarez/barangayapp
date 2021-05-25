package com.release.barangayapp.model;

import java.util.Date;

public class Announcement {

    private String title;
    private String content;
    private String currentDate;
    private String key;
    private int iconValue;

    public int getIconValue() { return iconValue; }

    public void setIconValue(int iconValue) { this.iconValue = iconValue; }

    public String getCurrentDate() { return currentDate; }

    public void setCurrentDate(String currentDate) { this.currentDate = currentDate; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
