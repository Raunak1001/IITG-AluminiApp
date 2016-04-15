package com.example.alumniapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Council extends Fragment {

    LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_council, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll1 = (LinearLayout) view.findViewById(R.id.ll_kshitiz);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFB(515867735, "kshitiz.anand");
            }
        });

        ll2 = (LinearLayout) view.findViewById(R.id.ll_arun);
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFB(693192740, "ajain.cfa");
            }
        });

        ll3 = (LinearLayout) view.findViewById(R.id.ll_deepak);
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFB(1584948327, "deepakiit");
            }
        });

        ll4 = (LinearLayout) view.findViewById(R.id.ll_dinesh);
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100000451986936"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/dineshkrlahoti")));
                }
            }
        });

        ll4 = (LinearLayout) view.findViewById(R.id.ll_ankush);
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100000530767136"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/ankush.dixit.18")));
                }
            }
        });

        ll5 = (LinearLayout) view.findViewById(R.id.ll_nikhil);
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100000555931079"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/originalnikhil")));
                }
            }
        });

        ll6 = (LinearLayout) view.findViewById(R.id.ll_arpit);
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100001274145574"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/arpit0410")));
                }
            }
        });

    }

    public void openFB(int id, String page) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + Integer.toString(id)));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + page)));
        }
    }
}