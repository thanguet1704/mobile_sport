package com.example.mobilesporta.fragment.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionTabFragment extends Fragment {

    TextView tvDescription;
    public DescriptionTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_description_tab, container, false);
        tvDescription = view.findViewById(R.id.textView_description);
        return view;
    }
}
