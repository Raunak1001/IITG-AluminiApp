package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by raunak on 15/4/16.
 */
public class Profile extends AppCompatActivity {
    SQLiteHandler db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);


        final SessionManager sessionManager;
        TextView email, branch, phone_number, name;
        Button edit, logout;
        sessionManager = new SessionManager(this);
        db = new SQLiteHandler(this);

        email = (TextView) findViewById(R.id.profile_email);
        branch = (TextView) findViewById(R.id.profile_branch);
        phone_number = (TextView) findViewById(R.id.profile_number);
        name = (TextView) findViewById(R.id.profile_name);
        edit = (Button) findViewById(R.id.profile_button);
        logout = (Button) findViewById(R.id.profile_logout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        email.setText(sessionManager.getEmail());
        branch.setText(sessionManager.getbranch());
        phone_number.setText(sessionManager.getnumber());
        name.setText(sessionManager.getname());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Update.class));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setLogin(false);
                sessionManager.clearAll();
                db.deleteUsers();
                Intent intent = new Intent(Profile.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                finish();
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}