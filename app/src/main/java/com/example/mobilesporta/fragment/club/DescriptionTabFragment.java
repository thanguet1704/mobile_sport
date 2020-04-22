package com.example.mobilesporta.fragment.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionTabFragment extends Fragment {

    public DescriptionTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_tab, container, false);
    }
}
