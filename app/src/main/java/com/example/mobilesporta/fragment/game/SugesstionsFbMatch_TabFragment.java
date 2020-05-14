package com.example.mobilesporta.fragment.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.model.MatchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugesstionsFbMatch_TabFragment extends Fragment {

    List<MatchModel> listMatch = new ArrayList<>();
    MatchModel match = new MatchModel("1", "2", "12", "12", "Ngày 12", "12h00", 90, "None", "keo nhe", "0320");


    public SugesstionsFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sugesstions_fb_match__tab, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lvSugesstion_Match_Fragment_ListMatch);

        listMatch.add(match);
        ItemFootballMatchAdapter itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatch);

        listView.setAdapter(itemFootballMatchAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}
