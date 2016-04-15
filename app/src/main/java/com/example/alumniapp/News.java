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

public class News extends Fragment {

    WebView wv;
    ProgressBar progrsess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progrsess= (ProgressBar) view.findViewById(R.id.progress_news);
        wv = (WebView) view.findViewById(R.id.news);
        progrsess.setVisibility(View.VISIBLE);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progrsess.setVisibility(View.GONE);
            }
        });
        wv.loadUrl("http://iitgaa.org/news/");
    }
}