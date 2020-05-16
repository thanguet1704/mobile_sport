package com.example.mobilesporta.data.service;

import androidx.annotation.NonNull;

import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MatchService {

    public void addMatch(MatchModel matchModel){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("matchs").push().setValue(matchModel);
    }
}
