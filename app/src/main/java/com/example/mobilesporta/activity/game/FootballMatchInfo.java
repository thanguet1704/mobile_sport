package com.example.mobilesporta.activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.R;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FootballMatchInfo extends AppCompatActivity {

    TextView txtHomeClubName;
    ImageView imgHomeClubName;
    TextView txtAwayClubName;
    ImageView imgAwayClubName;

    EditText edtDate;
    EditText edtTime;
    EditText edtTimeAmount;
    EditText edtPhone;
    EditText edtStadium;
    EditText edtDescription;



    String match_id;

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    MatchService matchService = new MatchService();
    Map<String, MatchModel> mapMatchs = matchService.getMapMatchs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_football_match_info);

        Intent idMatchFromFragment = getIntent();
        match_id = idMatchFromFragment.getStringExtra("match_id");

        connectView();
        showMatchInfo();
    }

    private void connectView() {
        txtHomeClubName = findViewById(R.id.txtMatchInfo_HomeClubName);
        txtAwayClubName = findViewById(R.id.tvMatchInfoAct_AwayClubName);
        imgHomeClubName = findViewById(R.id.imgMatchInfoAct_HomeClubAvt);
        imgAwayClubName = findViewById(R.id.imgMatchInfoAct_AwayClubAvt);

        edtDate = findViewById(R.id.edtMatchInfoAct_Date);
        edtTime = findViewById(R.id.edtMatchInfoAct_Time);
        edtTimeAmount = findViewById(R.id.edtMatchInfoAct_TimeAmount);
        edtPhone = findViewById(R.id.edtMatchInfoAct_PhoneNumber);
        edtStadium = findViewById(R.id.edtMatchInfoAct_Stadium);
        edtDescription = findViewById(R.id.edtMatchInfoAct_Description);
    }

    private ClubModel getClubById(String clubId, Map<String, ClubModel> map) {
        ClubModel clubModel = new ClubModel();
        for (Map.Entry<String, ClubModel> entry : map.entrySet()) {
            if(entry.getKey().equals(clubId)) {
                clubModel = entry.getValue();
            }
        }
        return  clubModel;
    }

    private  void setView(MatchModel matchModel) {

        Log.e("s", String.valueOf(mapClubs.size()));
        Log.e("ss", String.valueOf(mapMatchs.size()));

        if (!matchModel.getClub_home_id().equals("")) {
            ClubModel homeClubModel = getClubById(matchModel.getClub_home_id(), mapClubs);
            Log.e("s", homeClubModel.getImage());
            txtHomeClubName.setText(homeClubModel.getClub_name());
            Picasso.get().load(homeClubModel.getImage()).into(imgHomeClubName);
        }
        else {
            txtHomeClubName.setText("???");
        }


        if( !matchModel.getClub_away_id().equals("")) {
            ClubModel awayClubModel = getClubById(matchModel.getClub_away_id(), mapClubs);
            txtAwayClubName.setText(awayClubModel.getClub_name());
            Picasso.get().load(awayClubModel.getImage()).into(imgAwayClubName);
        }
        else {
            txtAwayClubName.setText("???");
        }



//        Picasso.get().load(awayClub.getImage()).into(imgAwayClub);

        edtDate.setText(matchModel.getDate());
        edtTime.setText(matchModel.getTime());
        edtTimeAmount.setText(matchModel.getTime_amount().toString());
        edtPhone.setText(matchModel.getPhone_number());
        edtStadium.setText(matchModel.getStadium_id());
        edtDescription.setText(matchModel.getDescription());
    }

    private void showMatchInfo() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query mQuery = mDatabase.child("matchs").orderByKey().equalTo(match_id);

        ValueEventListener valueEventListener = new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getKey().equals(match_id)) {
                            MatchModel matchModel = snapshot.getValue(MatchModel.class);
                            setView(matchModel);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mQuery.addListenerForSingleValueEvent(valueEventListener);
    }
}
