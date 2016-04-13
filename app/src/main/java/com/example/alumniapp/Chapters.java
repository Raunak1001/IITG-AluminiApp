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

/**
 * Created by ankit on 13/4/16.
 */
public class Chapters extends Fragment {

    WebView wv;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapters, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wv = (WebView) view.findViewById(R.id.wv);
        pd = ProgressDialog.show(getContext(), "", "Please wait. Loading ...", true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
            }
        });
        wv.loadUrl("http://iitgaa.org/chapters/");
    }
}
