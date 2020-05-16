package com.example.mobilesporta.fragment.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.ItemHistoryClubAdapter;
import com.example.mobilesporta.model.MatchModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryTabFragment extends Fragment {

    ListView listView;

    public HistoryTabFragment() {
        // Required empty public constructor
    }
    MatchModel match = new MatchModel("1", "2", "12", "12", "Ngày 12", "12h00", 90, "None", "keo nhe", "0320");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_tab, container, false);
        listView = view.findViewById(R.id.lv_history);


        String clubId = getArguments().getString("club_id");

        ArrayList<MatchModel> listMatch = new ArrayList<>();
        listMatch.add(match);
        ItemHistoryClubAdapter itemHistoryClubAdapter = new ItemHistoryClubAdapter(getActivity(), listMatch);
        listView.setAdapter(itemHistoryClubAdapter);
        return view;
    }
}
