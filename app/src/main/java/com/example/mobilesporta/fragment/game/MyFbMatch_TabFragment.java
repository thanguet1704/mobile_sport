package com.example.mobilesporta.fragment.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilesporta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFbMatch_TabFragment extends Fragment {

    public MyFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_fb_match__tab, container, false);
    }
}
