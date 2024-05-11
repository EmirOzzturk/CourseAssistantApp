package com.example.courseassistansapp;

public class User {
    private String name;
    private String email;
    private String password;
    private Integer studentID; // Integer allows null values
    private String title;
    private String ongoingEducation;
    private String contactEmailAddress;
    private String phoneNumber;
    private String imageUrl;

    public User(String name, String email, String password, Integer studentID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.studentID = studentID;
        if(email.endsWith("@std.yildiz.edu.tr")){
            //Öğrenci Girişi
            this.title = "Öğrenci";
        }else {
            //Admin girişi
            this.title = "Admin";
        }
    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        if (email.endsWith("yildiz.edu.tr")){
            //Akedemisyen Girişi
            this.title = "Akademisyen";
        } else {
            //Admin girişi
            this.title = "Admin";
        }
    }
    public User(){}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getOngoingEducation() {
        return ongoingEducation;
    }

    public void setOngoingEducation(String ongoingEducation) {
        this.ongoingEducation = ongoingEducation;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

