package com.example.courseassistansapp.view;


import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseassistansapp.Model.Course;
import com.example.courseassistansapp.courseAdapter;
import com.example.courseassistansapp.databinding.MainMenuInstructorBinding;

import java.util.ArrayList;
import java.util.List;

public class InstructorMainMenu extends AppCompatActivity{
    private MainMenuInstructorBinding binding;

    courseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainMenuInstructorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        List<Course> list = new ArrayList<>();
        list = getData();

        adapter = new courseAdapter(list, getApplication());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(InstructorMainMenu.this));
    }

    private List<Course> getData() {
        List<Course> list = new ArrayList<>();
        list.add(new Course("BLM 3520", "Mobil Programlamaya Giriş", 3,null, null));
        list.add(new Course("BLM 3130", "Bilgisayar", 2, null, null));
        list.add(new Course("BLM 3310", "Veritabanı", 1,null, null));

        return list;
    }

}
