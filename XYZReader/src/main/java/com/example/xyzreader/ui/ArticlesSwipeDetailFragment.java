package com.example.xyzreader.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesSwipeDetailFragment extends Fragment {


    public ArticlesSwipeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles_swipe_detail, container, false);
    }

}
