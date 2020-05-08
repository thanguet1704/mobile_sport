package com.example.mobilesporta.data.service;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.club.ClubProfile;
import com.example.mobilesporta.adapter.ItemClubAdapter;
import com.example.mobilesporta.model.ClubModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClubService {
    ArrayList<ClubModel> listClub = new ArrayList<>();

    public void addClub(ClubModel clubModel){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("clubs").push().setValue(clubModel);
    }

    public void getDataClub(String clubId, final TextView txtName, final TextView txtSlogan, final ImageView avatar) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query infoClub = mDatabase.orderByKey().equalTo(clubId);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClubModel club = snapshot.getValue(ClubModel.class);
                        txtName.setText(club.getClub_name());
                        txtSlogan.setText(club.getSlogan());
                        Picasso.get().load(club.getImage()).into(avatar);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoClub.addListenerForSingleValueEvent(valueEventListener);
    }

    public void updateAvatarCLub(String clubId, final String uri){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query updateAvatar = mDatabase.child(clubId);

        updateAvatar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("image").setValue(uri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
