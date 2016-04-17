package com.example.alumniapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raunak on 21/3/16.
 */
public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";

    String title,content;
    SQLiteHandler db;

    public GCMIntentService() {
        //Required initiation with the Sender ID

        super("21096671799");
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.d(TAG, "Device registered: regId = " + registrationId);


       ServerUtilities.register(context,registrationId);
    }

    /**
     * Method called on device un registred
     */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");

    }

    /**
     * Method called on Receiving a new description
     */
    @Override
    protected void onMessage(Context context, Intent intent) {

        SessionManager sessionManager;
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            db = new SQLiteHandler(context);

            // Initiating the instances of SessionManager and SQLiteHandler
//        Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received description");
            String noteJSON = intent.getExtras().getString("title");

            try {
                JSONObject obj = new JSONObject(noteJSON);
                Log.d("TAG", obj.toString());
                title = obj.getString("title");
                content = obj.getString("description");
                db.addAnn(title, content);
            } catch (JSONException e) {
                Log.d("TAG", "TEST");

                e.printStackTrace();
            }
            Intent new_intent = new Intent("com.sunshine.swc.BRAOADCAST");
            new_intent.putExtra("EXTRA", noteJSON);
            context.sendBroadcast(new_intent);


            Log.d("noti", title);
            generateNotification(context, title, content);


        }
    }
    /**
     * Method called on receiving a deleted description
     */
    @Override
    protected void onDeletedMessages(Context context, int total) {
       // Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "Received deleted messages notification");
        // notifies user
        // generateNotification(context, ,content);
    }

    /**
     * Method called on Error
     */
    @Override
    public void onError(Context context, String errorId) {
      //  Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        //Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();

        // log description
        Log.d(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a description.
     */
    private static void generateNotification(Context context, String notificationTitle, String notificationMessage) {

        // NotificationManager to handle Notifications
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent=new Intent(context,AnouncementView.class);
        notificationIntent.putExtra("title",notificationTitle);
        notificationIntent.putExtra("description",notificationMessage);


        // set intent so it does not start a new activity

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, (int) System.currentTimeMillis(), notificationIntent, 0);


        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationMessage)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(intent)
                    .setAutoCancel(true)
                    .build();
        }else{
            notification = new Notification.Builder(context)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationMessage)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(intent)
                    .setAutoCancel(true)
                    .getNotification();


        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);

    }

}
