package com.release.barangayapp.model;

public class UserRegisterObject {

    private String fullName;
    private int age;
    private int role;
    private String gender;
    private String address;

    public String getPhonenumber() { return Phonenumber; }

    public void setPhonenumber(String phonenumber) { this.Phonenumber = phonenumber; }

    private String Phonenumber;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}
