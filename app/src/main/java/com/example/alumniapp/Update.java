package com.example.alumniapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Update extends AppCompatActivity {

    EditText email,password,number;
    Button done;
    SessionManager sessionManager;
    MaterialBetterSpinner spinner;
String branch_string="";
    ProgressDialog dialog;
    final String[] error = new String[1];
    private final String TAG = Register.class.getSimpleName();
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        email= (EditText) findViewById(R.id.update_email);
        number= (EditText) findViewById(R.id.update_number);
        password= (EditText) findViewById(R.id.update_password);
        done= (Button) findViewById(R.id.update_button);
        layout= (LinearLayout) findViewById(R.id.update_layout);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Updating Profile");
        dialog.setCancelable(false);

        sessionManager=new SessionManager(this);
        email.setText(sessionManager.getEmail());
        password.setText(sessionManager.getpassword());
        number.setText(sessionManager.getnumber());
        spinner = (MaterialBetterSpinner) findViewById(R.id.choice);

        List<String> categories = new ArrayList<String>();
        categories.add("Biosciences and Bioengineering");

        categories.add("Chemical Engineering");

        categories.add("Department of Chemistry");

        categories.add("Civil Engineering");

        categories.add("ComputerScience & Engineering");

        categories.add("Design");

        categories.add("Electronics and Electrical Engineering");


        categories.add("Mathematics");
        categories.add("Mechanical Engineering");
        categories.add("Physics");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDropDown();
            }
        });



        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 1:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 2:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 3:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 4:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 5:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 6:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 7:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 8:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;

                    case 9:
                        branch_string = parent.getItemAtPosition(position).toString();

                        break;


                }

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().length()!=0 && password.getText().length()!=0 && !branch_string.equals("")){

                    if (number.getText().length()==10){
                        update_user();

                    }else {

                        Snackbar.make(v, "Enter a 10 digit Phone Number", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                }else{

                    Snackbar.make(v, "Enter All The Fields", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                }

            }
        });
    }

    public void update_user(){

        String tag_string_req = "req_tag_register";
        dialog.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email.getText().toString());
        params.put("number", number.getText().toString());
        params.put("branch", branch_string);
        params.put("password", password.getText().toString());
        params.put("old_email", sessionManager.getEmail());


        JSONObject json = new JSONObject(params);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://ankit21.16mb.com/alum/updateUser.php",
                json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                dialog.hide();
                Log.d(TAG, response.toString());
                try {

                    error[0] = response.getString("error");


                    if (error[0].equalsIgnoreCase("false")) {

                        setUser();
                        //setrUser(details);

                        Intent intent =new Intent(Update.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Snackbar.make(layout, "Profile Updated", Snackbar.LENGTH_SHORT).show();


                        finish();
                        startActivity(intent);



                        Log.d(TAG, response.toString());

                    } else {
                        Log.d(TAG, response.toString());

                        Snackbar.make(layout, "User Already Regisetered with this email id", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());

                dialog.hide();
                Snackbar.make(layout, "Check Your Internet Connection", Snackbar.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);


    }


    public void setUser() throws JSONException {
        //  db.addAnn((String) notice.get("title"),notice.getString("description"));
        sessionManager.setEmail(email.getText().toString());
        sessionManager.setPAssword(password.getText().toString());
        sessionManager.setBranch(branch_string);
        sessionManager.setNumber(number.getText().toString());

    }


    }

