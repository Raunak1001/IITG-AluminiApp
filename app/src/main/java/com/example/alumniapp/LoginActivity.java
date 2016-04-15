package com.example.alumniapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    TextView informationText;
    String  usernameValue, passwordValue;
    AutoCompleteTextView username;
    EditText password;
    SQLiteHandler db;
Button login;
    Intent intent;

    SessionManager sessionManager;
    ScrollView layout;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initiate();

        ArrayList<String> usernames = new ArrayList();
        String[] user1 = sessionManager.getUsername().split(",");
        if (user1 != null) {
            for (int q = 0; q < user1.length; q++) {
                usernames.add(user1[q]);
            }
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, usernames);
        username.setAdapter(adapter);
        username.setThreshold(1);

        Intent intent = null;
        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in");
        dialog.setCancelable(false);
        password.setTransformationMethod(new PasswordTransformationMethod());

        informationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register.class));
            }
        });

        intent=new Intent(this,MainActivity.class);
        if (sessionManager.isLoggedIn()) {
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        if (password.length() > 0) {
        }
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    password.setSelection(password.length());
                } else {
                    password.setSelection(password.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void initiate() {

        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        informationText = (TextView) findViewById(R.id.information_text);
        layout = (ScrollView) findViewById(R.id.login_screen);
        sessionManager = new SessionManager(this);
        db = new SQLiteHandler(this);
        login= (Button) findViewById(R.id.login);
intent = new Intent(this,MainActivity.class);
    }

    public void login(View v) {
        usernameValue = username.getText().toString().trim();
        passwordValue = password.getText().toString().trim();
        if (!usernameValue.isEmpty() && !passwordValue.isEmpty()) {
            new loginUserTask().execute();

        } else {
            Snackbar.make(v, "Enter All The Fields", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    public class loginUserTask extends AsyncTask<Void, String, Void> {


        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String tag_string_req = "req_tag_login";
            HashMap<String, String> loginParams = new HashMap<String, String>();
            loginParams.put("email", usernameValue);
            loginParams.put("password", passwordValue);
            JSONObject credentialsJSON = new JSONObject(loginParams);
            Log.d(TAG, credentialsJSON.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "http://iitgaa.hol.es/Login.php",
                    credentialsJSON,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d("TEST", response.toString());
                                String userId = null;
                                String error = response.getString("error");
                                if (error.equals("false")) {
                                    sessionManager.setUsernames(usernameValue);
                                    JSONObject user = new JSONObject(response.getString("user"));
                                        JSONArray annoucements = new JSONArray(user.getString("announcements"));
                                    JSONObject details = new JSONObject(user.getString("details"));
                                    setUser(details);
                                    addAnnouncements(annoucements);
                                        publishProgress("1");
                                        finish();

                                } else {
                                    publishProgress("2", response.getString("error_msg"));
                                }
                            } catch (Exception e) {
                                publishProgress("4");
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    publishProgress("3");

                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //progressBar.setVisibility(View.GONE);
            dialog.hide();
            switch (values[0]) {
                case "1":
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    sessionManager.setLogin(true);
                    break;

                case "2":
                    Snackbar.make(layout, values[1], Snackbar.LENGTH_SHORT).show();
                    break;

                case "3":
                    Snackbar.make(layout,"Check Your Internet Connection", Snackbar.LENGTH_SHORT).show();
                    break;

                case "4":
                    Snackbar.make(layout, "Check Your Internet Connection", Snackbar.LENGTH_SHORT).show();
                    break;

            }
        }
    }


    public void addAnnouncements(JSONArray notices) throws JSONException {
        for (int i = 0; i < notices.length(); i++) {
            JSONObject notice = notices.getJSONObject(i);
            db.addAnn((String) notice.get("title"),notice.getString("description"));
        }
    }
    public void setUser(JSONObject details) throws JSONException {
        //  db.addAnn((String) notice.get("title"),notice.getString("description"));
        sessionManager.setEmail(details.getString("email"));
        sessionManager.setPAssword(details.getString("password"));
        sessionManager.setBranch(details.getString("branch"));
        sessionManager.setNumber(details.getString("number"));
        sessionManager.setName(details.getString("name"));
    }


}

