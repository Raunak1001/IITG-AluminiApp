package com.example.alumniapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Council extends Fragment {

    public Council() {
        // Required empty public constructor
    }
//
//    ListView list;
//    String[] web = {
//            "Kshitiz Anand",
//            "Kshitiz Anand",
//            "Kshitiz Anand",
//            "Kshitiz Anand",
//            "Kshitiz Anand",
//            "Kshitiz Anand",
//            "Kshitiz Anand",
//    } ;
//    Integer[] imageId = {
//            R.drawable.img,
//            R.drawable.img,
//            R.drawable.img,
//            R.drawable.img,
//            R.drawable.img,
//            R.drawable.img,
//            R.drawable.img,
//
//    };
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_council, container, false);
    }

}