package com.example.courseassistansapp;

import java.util.Calendar;
import java.util.Date;

public class Course {
    private int Id;
    private String courseName;
    private Date date;
    private int numberOfGroups;
    private User[] instructors;
    private User[] students;


    public Course(int id, String courseName, int numberOfGroups, User[] instructors, User[] students) {
        Calendar calendar = Calendar.getInstance();

        Id = id;
        this.courseName = courseName;
        this.numberOfGroups = numberOfGroups;
        this.instructors = instructors;
        this.students = students;
        this.date = calendar.getTime();
    }

    public Course() {}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    public User[] getInstructors() {
        return instructors;
    }

    public void setInstructors(User[] instructors) {
        this.instructors = instructors;
    }

    public User[] getStudents() {
        return students;
    }

    public void setStudents(User[] students) {
        this.students = students;
    }
}
