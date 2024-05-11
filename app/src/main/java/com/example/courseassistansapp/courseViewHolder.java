package com.example.courseassistansapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class courseViewHolder extends RecyclerView.ViewHolder {

    TextView courseId;
    TextView courseName;
    TextView courseDate;
    ImageButton reportCourse;
    ImageView imageView;
    View view;
    courseViewHolder(View itemview){
        super(itemview);

        courseDate = (TextView) itemview.findViewById(R.id.courseDate);
        courseId = (TextView) itemview.findViewById(R.id.courseId);
        courseName = (TextView) itemview.findViewById(R.id.courseName);
        imageView = (ImageView) itemview.findViewById(R.id.courseBackground);
        reportCourse = (ImageButton) itemview.findViewById(R.id.reportCourse);

        view = itemview;
    }
}
