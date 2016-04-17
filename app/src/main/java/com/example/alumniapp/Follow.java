package com.example.alumniapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by ankit on 17/4/16.
 */
public class Follow extends Fragment {

    ImageButton fb, li, gp, tw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fb = (ImageButton) view.findViewById(R.id.fb);
        tw = (ImageButton) view.findViewById(R.id.tw);
        li = (ImageButton) view.findViewById(R.id.li);
        gp = (ImageButton) view.findViewById(R.id.gp);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/176151939098739"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/iitgaa")));
                }
            }
        });

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name=iitgaa"));
                    startActivity(intent);

                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/iitgaa")));
                }
            }
        });

        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
                    intent.putExtra("customAppUri", "100236595920286364752");
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://plus.google.com/100236595920286364752/posts")));
                }
            }
        });

        li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/iit-guwahati-alumni-association?trk=company_logo")));
            }
        });

    }
}
