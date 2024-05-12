package com.example.courseassistansapp;

import com.example.courseassistansapp.Model.Course;

import java.util.Comparator;

public class CourseDateComparator implements Comparator<Course> {
    @Override
    public int compare(Course course1, Course course2) {
        // Compare the 'date' properties of course1 and course2
        return course1.getDate().compareTo(course2.getDate());
    }
}
