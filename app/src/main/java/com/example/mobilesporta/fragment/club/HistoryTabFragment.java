package com.example.mobilesporta.fragment.club;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.ItemHistoryClubAdapter;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryTabFragment extends Fragment {

    ListView listView;
    public HistoryTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_tab, container, false);
        listView = view.findViewById(R.id.lv_history);


        String clubId = getArguments().getString("club_id");
        Log.d("id", clubId);
        renderMatchProfile(clubId);

        return view;
    }

    public void renderMatchProfile(String clubId){
        final ArrayList<MatchModel> listMatch = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchQuery = mDatabase.orderByChild("club_home_id").equalTo(clubId);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        if (matchModel.getClub_away_id().length() != 0){
                            listMatch.add(matchModel);
                        }

                        ItemHistoryClubAdapter itemHistoryClubAdapter = new ItemHistoryClubAdapter(getActivity(), listMatch);
                        listView.setAdapter(itemHistoryClubAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchQuery.addListenerForSingleValueEvent(valueEventListener);

        Query query = mDatabase.orderByChild("club_away_id").equalTo(clubId);
        ValueEventListener clubAway = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        if (matchModel.getClub_away_id().length() != 0){
                            listMatch.add(matchModel);
                        }

                        ItemHistoryClubAdapter itemHistoryClubAdapter = new ItemHistoryClubAdapter(getActivity(), listMatch);
                        listView.setAdapter(itemHistoryClubAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        query.addListenerForSingleValueEvent(clubAway);
    }
}
