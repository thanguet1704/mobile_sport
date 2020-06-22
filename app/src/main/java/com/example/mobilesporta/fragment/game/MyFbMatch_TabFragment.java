package com.example.mobilesporta.fragment.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchCreateNew;
import com.example.mobilesporta.activity.game.FootballMatchInfo;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFbMatch_TabFragment extends Fragment {

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();
    Map<String, ClubModel> mapMyClubs = clubService.getMyMapClubs();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView lvMatch;
    LinearLayout llNone;

    public MyFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_my_fb_match__tab, container, false);
        connectView(view);

        showMyListMatch();


        return view;
    }

    private void connectView(View view) {
        lvMatch = (ListView) view.findViewById(R.id.lvMy_Fb_Match_Fragment_ListMatch);
        llNone = view.findViewById(R.id.ll_none);
    }


    private void showMyListMatch() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("matchs");
        databaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    ArrayList<MatchModel> listMatch = new ArrayList<>();
                    final ArrayList<String> listMatchId = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);

                        if(user.getUid().equals(match.getUser_created_id()) || mapMyClubs.containsKey(match.getClub_away_id()) ) {
                            listMatch.add(match);
                            listMatchId.add(snapshot.getKey());
                        }
                    }
                    if (listMatch.size() == 0){
                        llNone.setVisibility(View.VISIBLE);
                    }else{
                        llNone.setVisibility(View.GONE);
                    }

                    ItemFootballMatchAdapter itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatch, mapClubs);
                    lvMatch.setAdapter(itemFootballMatchAdapter);
                    lvMatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), FootballMatchInfo.class);
                            intent.putExtra("match_id", listMatchId.get(position));
                            startActivity(intent);
                        }
                    });
                }else{
                    llNone.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
