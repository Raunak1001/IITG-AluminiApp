package com.example.alumniapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by ankit on 13/4/16.
 */
public class Chapters extends Fragment {

    WebView wv;
ProgressBar progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapters, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress= (ProgressBar) view.findViewById(R.id.chapters_progress);
        wv = (WebView) view.findViewById(R.id.wv);
progress.setVisibility(View.VISIBLE);        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
progress.setVisibility(View.GONE);            }
        });
        wv.loadUrl("http://iitgaa.org/chapters/");
    }
}
