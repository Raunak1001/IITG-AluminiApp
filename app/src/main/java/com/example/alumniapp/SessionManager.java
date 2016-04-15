package com.example.alumniapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


/**
 * Shared Preferences Class used for storing all session management values
 * like keeping the user logged in, school Name, etc.
 */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Alumini";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String KEY_EMAIL="email";
    private static final String KEY_PASSWORD="password";
    private static final String KEY_BRANCH="branch";
    private static final String KEY_NUMBER="number";
    private static final String KEY_SAVED_USERNAME = "savedUsername";
    private static final String KEY_NAME="name";



    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setUsernames(String username) {
        int flag = 0;
        String oldUsername = getUsername();
        String[] usernames = oldUsername.split(",");
        for(int i = 0; i < usernames.length; i++){
            if(usernames[i].equals(username)){
                flag = 1;
                break;
            }
        }

        if(flag==0) {
            if (!oldUsername.equals("")) {
                editor.putString(KEY_SAVED_USERNAME, oldUsername + "," + username);
            } else {
                editor.putString(KEY_SAVED_USERNAME, username);
            }
            editor.commit();

            Log.d(TAG, "User login session modified!");
        }
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);


        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setEmail(String email) {

        editor.putString(KEY_EMAIL, email);
        editor.commit();

    }

    public void setPAssword(String password) {

        editor.putString(KEY_PASSWORD, password);
        editor.commit();

    }

    public void setBranch(String branch) {

        editor.putString(KEY_BRANCH, branch);
        editor.commit();

    }

    public void setNumber(String number) {

        editor.putString(KEY_NUMBER, number);
        editor.commit();

    }
    public void setName(String name) {

        editor.putString(KEY_NAME, name);
        editor.commit();

    }




    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public String getEmail() {
        return pref.getString(KEY_EMAIL,"");
    }

    public String getpassword() {
        return pref.getString(KEY_PASSWORD,"");
    }

    public String getbranch() {
        return pref.getString(KEY_BRANCH,"");
    }

    public String getnumber() {
        return pref.getString(KEY_NUMBER,"");
    }
    public String getUsername() {
        return pref.getString(KEY_SAVED_USERNAME, "");
    }

    public String getname() {
        return pref.getString(KEY_NAME, "");
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

}
