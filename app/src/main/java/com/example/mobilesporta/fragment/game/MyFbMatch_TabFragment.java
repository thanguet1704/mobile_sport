package com.example.mobilesporta.fragment.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchCreateNew;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.MatchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFbMatch_TabFragment extends Fragment {

    MatchService matchService = new MatchService();
    List<MatchModel> listMatch = matchService.getMyListMatch();

    Button btnCreateNewMatch;
    ListView lvMatch;

    public MyFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_my_fb_match__tab, container, false);
        connectView(view);

        Log.e("msg", "my");
        ItemFootballMatchAdapter itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatch);
        lvMatch.setAdapter(itemFootballMatchAdapter);

        showMyListMatch();

        btnCreateNewMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMatch();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void createMatch() {
        startActivity(new Intent(getActivity(), FootballMatchCreateNew.class));
    }

    private void connectView(View view) {
        btnCreateNewMatch = (Button) view.findViewById(R.id.btnMy_Fb_Match_Fragment_AddNewMatch);
        lvMatch = (ListView) view.findViewById(R.id.lvMy_Fb_Match_Fragment_ListMatch);
    }

    private void showMyListMatch() {
        ItemFootballMatchAdapter itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatch);
        lvMatch.setAdapter(itemFootballMatchAdapter);
    }
}
