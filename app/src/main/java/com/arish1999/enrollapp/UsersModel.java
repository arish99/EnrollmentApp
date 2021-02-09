package com.arish1999.enrollapp;

public class UsersModel {
    private String fName;
    private String lName;
    private String date_of_birth;
    private String gender;
    private String country;
    private String state;
    private String homeTown;
    private String phoneNumber;
    private String telephoneNumber;
    private String profileImage;

    public UsersModel() {
    }

    public UsersModel(String fName, String lName, String date_of_birth, String gender, String country, String state, String homeTown, String phoneNumber, String telephoneNumber, String profileImage) {
        this.fName = fName;
        this.lName = lName;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.country = country;
        this.state = state;
        this.homeTown = homeTown;
        this.phoneNumber = phoneNumber;
        this.telephoneNumber = telephoneNumber;
        this.profileImage = profileImage;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
