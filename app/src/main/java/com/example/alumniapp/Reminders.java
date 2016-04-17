package com.example.alumniapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Reminders extends Fragment {


    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString("EXTRA");
            // Waking up mobile if it is sleeping
            Log.d("Message", newMessage);
            try {
                layout.setVisibility(View.GONE);
                JSONObject noticeJSON = new JSONObject(newMessage);
                HashMap<String, String> newNotice = new HashMap<>();
                newNotice.put("title", noticeJSON.getString("title"));
                newNotice.put("description", noticeJSON.getString("description"));
                announcemetAdapter.add(announcemetAdapter.getItemCount(), newNotice);

                recycleView.scrollToPosition(announcemetAdapter.getItemCount()-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            WakeLocker.release();
        }
    };

    private AnnouncementAdapter announcemetAdapter;
    RecyclerView recycleView;
    private LinearLayoutManager layoutmanager;
    HashMap<String, String> params;
    ArrayList<HashMap<String, String>> list;
    SQLiteHandler database;
    LinearLayout layout;
    public Reminders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleView = (RecyclerView) view.findViewById(R.id.announcement_recyclerView);
        params = new HashMap<>();
        list = new ArrayList<>();
layout= (LinearLayout) view.findViewById(R.id.linear_layout);

        layoutmanager = new LinearLayoutManager(getActivity());
        layoutmanager.setReverseLayout(true);
        recycleView.setLayoutManager(layoutmanager);
        recycleView.setHasFixedSize(false);

        database = new SQLiteHandler(getActivity().getApplicationContext());
       // database.addAnn("Test","Body");

        list = database.getAnn();

        if(list.size()==0){

            layout.setVisibility(View.VISIBLE);
        }
        announcemetAdapter = new AnnouncementAdapter(list, getActivity());
        getActivity().registerReceiver(mHandleMessageReceiver, new IntentFilter("com.sunshine.swc.BRAOADCAST"));


        recycleView.setAdapter(announcemetAdapter);
        recycleView.scrollToPosition(announcemetAdapter.getItemCount() - 1);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(mHandleMessageReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


}
