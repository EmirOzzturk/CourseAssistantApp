package com.example.courseassistansapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.courseassistansapp.Model.Course;
import com.example.courseassistansapp.R;
import com.example.courseassistansapp.databinding.ActivityAccountBinding;
import com.example.courseassistansapp.databinding.ActivityAddCourseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddCourse extends AppCompatActivity {

    ActivityAddCourseBinding binding;
    DatabaseReference myDatabase = FirebaseDatabase.getInstance("https://course-assistants-app-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference();
    FirebaseAuth Auth = FirebaseAuth.getInstance();
    Course course = new Course();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                course = readFromScreen();
                course.addInstructor(Auth.getUid());
                if (course.getId().isEmpty() || course.getCourseName().isEmpty() ||
                        binding.editTextGroupNumber.getText().toString().isEmpty() )
                {
                    Toast.makeText(AddCourse.this, "Bütün alanlar dolu olmalı!!", Toast.LENGTH_SHORT).show();
                }else{
                    //courses altına ders kodu ile git
                    myDatabase.child("courses").child(course.getId()).setValue(course);
                    Toast.makeText(AddCourse.this, "Ders başarıyla eklendi", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddCourse.this, InstructorMainMenu.class));
                    finish();
                }

            }
        });
    }

    private Course readFromScreen(){
        course.setCourseName(binding.editTextCourseName.getText().toString());
        course.setId(binding.editTextCourseId.getText().toString());
        course.setNumberOfGroups(Integer.valueOf(binding.editTextGroupNumber.getText().toString()));
        course.setDate(Calendar.getInstance().getTime());
        return course;
    }
}