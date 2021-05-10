package com.release.barangayapp.model;

import java.util.Date;

public class Announcement {

    private String title;
    private String content;
    private String createdAt;
    private String userId;
    private String type;
    private String currentDate;

    public String getCurrentDate() { return currentDate; }

    public void setCurrentDate(String currentDate) { this.currentDate = currentDate; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
