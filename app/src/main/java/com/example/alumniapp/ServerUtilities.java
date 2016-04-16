package com.example.alumniapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Handling GCM registrations on our server for
 * the Student Side. When a new registration Id
 * is assigned to a student, register function
 * is called to register the user's GCM Registration
 * Id on our server.
 */
public final class ServerUtilities {
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();
    public static JSONObject jsonToSend;
    public static Context contextReal;

    /**
     * Register this account/device pair within the server.
     */
    public static void register(final Context context, final String regId) {
        contextReal = context;


        Log.d("Register", "registering device (regId = " + regId + ")");
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);


        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d("Register", "Attempt #" + i + " to register");
            try {

                jsonToSend = new JSONObject(params);
                register_post();
                GCMRegistrar.setRegisteredOnServer(context, true);

                return;
            } catch (Exception e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.d("FAILED", "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d("SLEEPING", "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d("INTERUPTED", "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }

    }

    /**
     * Unregister this account/device pair within the server.
     */

    public static void register_post() {
        String tag_string_req = "req_register";
        Log.d("Sent", jsonToSend.toString());
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                "http://iitgaa.hol.es/register.php", jsonToSend, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response Server", response.toString());

                try {
                    String error = response.getString("error");
                    if (error.equals("false")) {
                        Log.d("REGISTERED", "Registered On GCM");
                    } else {
                        //  Toast.makeText(contextReal, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error submit review", "Login Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        Log.d("DSF", "DSFASF");
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
