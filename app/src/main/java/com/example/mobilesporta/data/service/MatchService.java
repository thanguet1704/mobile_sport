package com.example.mobilesporta.data.service;

import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MatchService {

    ArrayList<MatchModel> listClub = new ArrayList<>();

    public void addMatch(MatchModel matchModel){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("matchs").push().setValue(matchModel);
    }
}
