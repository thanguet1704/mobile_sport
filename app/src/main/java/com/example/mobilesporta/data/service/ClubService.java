package com.example.mobilesporta.data.service;

import android.util.Log;
import android.widget.Toast;

import com.example.mobilesporta.model.ClubModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClubService {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public void addClub(ClubModel clubModel){
        mDatabase.child("clubs").push().setValue(clubModel);
    }
}
