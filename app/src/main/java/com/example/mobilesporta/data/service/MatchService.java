package com.example.mobilesporta.data.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

import com.example.mobilesporta.model.MatchModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchService {
    List<MatchModel> listMatchModels = new ArrayList<>();
    List<String> listMatchId = new ArrayList<>();
    Map<String, MatchModel> mapMatchs = new HashMap<>();

    public void addMatch(MatchModel matchModel){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("matchs").push().setValue(matchModel);
    }

    public List<MatchModel> getListMatch() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);

                        listMatchModels.add(match);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchModels;
    }

    public List<MatchModel> getMyListMatch() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String user_id = user.getUid();
                Log.e("us_id", user_id);

                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        if (match.getUser_created_id().equals(user_id)) {

                            listMatchModels.add(match);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchModels;
    }

    public List<String> getListMatchId() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        listMatchId.add(snapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchId;
    }

    public List<String> getMyListMatchId() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String user_id = user.getUid();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        if (match.getUser_created_id().equals(user_id)) {

                            listMatchId.add(snapshot.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchId;
    }

    public Map<String, MatchModel> getMapMatchs() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query a = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        MatchModel clubModel = snapshot.getValue(MatchModel.class);
                        mapMatchs.put(snapshot.getKey(), clubModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        a.addListenerForSingleValueEvent(valueEventListener);

        return mapMatchs;
    }
}
