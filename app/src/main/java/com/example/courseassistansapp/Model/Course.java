package com.example.courseassistansapp.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Course {
    private String Id;
    private String courseName;
    private Date date;
    private int numberOfGroups;
    private ArrayList<String> instructors;
    private ArrayList<String> students;
    private String completionStatus;



    public Course(String Id, String courseName, Date date, int numberOfGroups) {
        this.Id = Id;
        this.courseName = courseName;
        this.numberOfGroups = numberOfGroups;
        this.date = date;
    }

    public Course() {}


    public void addInstructor(String user){
        // 1. Check if instructors is null (initialize if it is)
        if (instructors == null) {
            instructors = new ArrayList<String>();
        }
        // 2. Add the new element to the ArrayList
        instructors.add(user);
    }

    public void addStudent(String user){
        if (students == null){
            students = new ArrayList<String>();
        }
        students.add(user);
    }
    public String  getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
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

    public ArrayList<String> getInstructors() {
        return instructors;
    }

    public void setInstructors(ArrayList<String> instructors) {
        this.instructors = instructors;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Id='" + Id + '\'' +
                ", courseName='" + courseName + '\'' +
                ", date=" + date +
                ", numberOfGroups=" + numberOfGroups +
                ", instructors=" + instructors +
                ", students=" + students +
                '}';
    }
}


