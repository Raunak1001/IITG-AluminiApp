package com.example.alumniapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

/**
 * Created by raunak on 14/4/16.
 */
public class Register extends AppCompatActivity {

    MaterialBetterSpinner spinner;
    EditText pass, email, phone;
    Button register;
    String pass_string, email_string, phone_string;
    private final String TAG = Register.class.getSimpleName();
    ProgressDialog dialog;
    LinearLayout layout;
    String branch_string = "";
    SQLiteHandler db;
    SessionManager sessionmanager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        pass = (EditText) findViewById(R.id.register_password);
        email = (EditText) findViewById(R.id.register_emailId);
        phone = (EditText) findViewById(R.id.register_phone_number);
        register = (Button) findViewById(R.id.register);
        db = new SQLiteHandler(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Registering...");
        dialog.setCancelable(false);
        layout = (LinearLayout) findViewById(R.id.register_layout);
        spinner = (MaterialBetterSpinner) findViewById(R.id.choice);
        sessionmanager = new SessionManager(this);
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


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_string = pass.getText().toString();
                email_string = email.getText().toString();
                phone_string = phone.getText().toString();


                if (!branch_string.equals("") && !email_string.isEmpty() && !pass_string.isEmpty() && !phone_string.isEmpty()) {
                    if (phone_string.length() == 10) {


                        registerUser();


                    } else {
                        Snackbar.make(v, "Enter a 10 digit Phone Number", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }


                } else {

                    Snackbar.make(v, "Enter All The Fields", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    Log.d(TAG, branch_string + "  " + email_string + " " + pass_string + "  " + phone_string);

                }

            }
        });


    }


    public void registerUser() {
        final String[] error = new String[1];
        String tag_string_req = "req_tag_register";
        dialog.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email_string);
        params.put("number", phone_string);
        params.put("branch", branch_string);
        params.put("password", pass_string);

        JSONObject json = new JSONObject(params);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://ankit21.16mb.com/alum/registerUser.php",
                json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                dialog.hide();
                Log.d(TAG, response.toString());

                try {
                    error[0] = response.getString("error");


                    if (error[0].equalsIgnoreCase("false")) {
                        JSONObject user = new JSONObject(response.getString("user"));
                        JSONArray annoucements = new JSONArray(user.getString("announcements"));
                        JSONObject details = new JSONObject(user.getString("details"));
                        setUser(details);
                        addAnnouncements(annoucements);
                        //setrUser(details);
                        sessionmanager.setLogin(true);

                        Intent intent =new Intent(Register.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

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

    public void addAnnouncements(JSONArray notices) throws JSONException {
        for (int i = 0; i < notices.length(); i++) {
            JSONObject notice = notices.getJSONObject(i);
            db.addAnn((String) notice.get("title"), notice.getString("description"));
        }
    }

    public void setUser(JSONObject details) throws JSONException {
        //  db.addAnn((String) notice.get("title"),notice.getString("description"));
        sessionmanager.setEmail(details.getString("email"));
        sessionmanager.setPAssword(details.getString("password"));
        sessionmanager.setBranch(details.getString("branch"));
        sessionmanager.setNumber(details.getString("number"));

    }
}


