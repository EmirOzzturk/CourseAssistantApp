package com.example.courseassistansapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.courseassistansapp.Model.Course;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class courseAdapter extends RecyclerView.Adapter<courseViewHolder> {

    List<Course> list = Collections.emptyList();
    Context context;
    String dateInFormat;  // 12.05.2024
    public courseAdapter(List<Course> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public courseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View courseView = inflater.inflate(R.layout.course_card, parent, false);

        courseViewHolder viewHolder = new courseViewHolder(courseView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final courseViewHolder viewHolder, final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        dateInFormat = list.get(index).getDate().toString();
        viewHolder.courseId.setText(list.get(index).getId());
        viewHolder.courseDate.setText(dateInFormat);
        viewHolder.courseName.setText(list.get(index).getCourseName());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(view.getContext(), "Course clicked at position: " + index, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.reportCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
