package com.example.courseassistansapp.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.courseassistansapp.Model.Course;
import com.example.courseassistansapp.Model.User;
import com.example.courseassistansapp.R;
import com.example.courseassistansapp.courseAdapter;
import com.example.courseassistansapp.databinding.MainMenuInstructorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InstructorMainMenu extends AppCompatActivity{
    private MainMenuInstructorBinding binding;
    DatabaseReference myDatabase = FirebaseDatabase.getInstance("https://course-assistants-app-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference();
    courseAdapter adapter;
    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainMenuInstructorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        List<Course> list = new ArrayList<>();

        ValueEventListener userInfoListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.child("courses").getChildren()) {
                    list.add(childSnapshot.getValue(Course.class));
                }
                adapter = new courseAdapter(list, getApplication());
                binding.recyclerView.setAdapter(adapter);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(InstructorMainMenu.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "veri okunamadı", error.toException());
            }
        };
        //Listener'ı Database'e ekle
        myDatabase.addValueEventListener(userInfoListener);



        binding.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstructorMainMenu.this, AccountActivity.class));
                finish();
            }
        });

        binding.addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstructorMainMenu.this, AddCourse.class));
                finish();
            }
        });

        if(AccountActivity.currentPhotoPath != null){
            File f = new File(AccountActivity.currentPhotoPath);
            binding.profilePhoto.setImageURI(Uri.fromFile(f));
            binding.profilePhoto.setBackgroundColor(getColor(R.color.green));
        }

    }
}
