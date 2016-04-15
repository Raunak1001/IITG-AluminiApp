package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by raunak on 15/4/16.
 */
public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);
        SessionManager sessionManager;
        TextView email, branch, phone_number,name;
        Button edit;
        sessionManager = new SessionManager(this);

        email = (TextView) findViewById(R.id.profile_email);
        branch = (TextView) findViewById(R.id.profile_branch);
        phone_number = (TextView) findViewById(R.id.profile_number);
        name= (TextView) findViewById(R.id.profile_name);
        edit = (Button) findViewById(R.id.profile_button);


        email.setText(sessionManager.getEmail());
        branch.setText(sessionManager.getbranch());
phone_number.setText(sessionManager.getnumber());
        name.setText(sessionManager.getname());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Update.class));
            }
        });

    }
}
