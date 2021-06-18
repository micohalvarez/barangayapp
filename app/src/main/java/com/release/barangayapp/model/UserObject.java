package com.release.barangayapp.model;

public class UserObject {

    private String fullName;
    private int role;
    private String address;
    private String userId;
    private String email;

    public String getPhonenumber() { return Phonenumber; }

    public void setPhonenumber(String phonenumber) { this.Phonenumber = phonenumber; }

    private String Phonenumber;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
