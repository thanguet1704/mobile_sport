package com.example.mobilesporta.fragment.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugesstionsFbMatch_TabFragment extends Fragment {

    MatchService matchService = new MatchService();
    List<MatchModel> listMatch = matchService.getListMatch();

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    ListView listView;

    public SugesstionsFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sugesstions_fb_match__tab, container, false);
        connectView(view);

        showListMatch();
        // Inflate the layout for this fragment
        return view;
    }

    private void connectView(View view) {
        listView = (ListView) view.findViewById(R.id.lvSugesstion_Match_Fragment_ListMatch);
    }

    private void showListMatch() {
        ItemFootballMatchAdapter itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatch, mapClubs);
        listView.setAdapter(itemFootballMatchAdapter);
    }
}
