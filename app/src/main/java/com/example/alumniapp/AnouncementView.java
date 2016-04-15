package com.example.alumniapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AnouncementView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anouncement_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_view);

        TextView title,description;
        title= (TextView) findViewById(R.id.notice_view_title);
        description= (TextView) findViewById(R.id.notice_view_content);
setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));


    }



}
