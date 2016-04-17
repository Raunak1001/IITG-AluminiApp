/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.example.alumniapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "scholarmate";

    //User Tables
    private static final String TABLE_ANNOUNCEMENTS = "announcements";

    private static final String KEY_ANN_TITLE = "title";

    private static final String KEY_ANN_DESCRIPTION = "description";
    private static final String KEY_ID = "id";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ANNOUNCEMENTS_TABLE = "CREATE TABLE " + TABLE_ANNOUNCEMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ANN_TITLE + " TEXT,"
                + KEY_ANN_DESCRIPTION + " TEXT"
                + ")";


        db.execSQL(CREATE_ANNOUNCEMENTS_TABLE);
        Log.d(TAG, "Database tables created");
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENTS);

        // Create tables again
        onCreate(db);
    }

    public void addAnn(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ANN_TITLE, title);
        values.put(KEY_ANN_DESCRIPTION, description);
        // Inserting Row
        long id = db.insert(TABLE_ANNOUNCEMENTS, null, values);
        ; // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    public ArrayList<HashMap<String, String>> getAnn() {
        ArrayList<HashMap<String, String>> personalNotes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ANNOUNCEMENTS;
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Move to first row
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            HashMap<String, String> notice = new HashMap<>();
            notice.put(KEY_ANN_TITLE, cursor.getString(1));
            notice.put(KEY_ANN_DESCRIPTION, cursor.getString(2));
            personalNotes.add(i, notice);
            cursor.moveToNext();
            i++;

        }
        cursor.close();
        ;
        // return user
        Log.d(TAG, "Fetching student List from Sqlite: " + personalNotes.toString());

        return personalNotes;
    }

    public void remAnn(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "DELETE FROM " + TABLE_ANNOUNCEMENTS + " WHERE " + KEY_ANN_TITLE + " = '" + title + "' AND " + KEY_ANN_DESCRIPTION + " = '" + description+"'";
        db.execSQL(selectQuery);
        Log.d(TAG, "New user inserted into sqlite: ");
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENTS);

        Log.d(TAG, "Deleted all user info from sqlite");
        onCreate(db);
    }
}
