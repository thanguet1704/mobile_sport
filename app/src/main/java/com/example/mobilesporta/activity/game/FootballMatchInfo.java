package com.example.mobilesporta.activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.mobilesporta.model.StadiumModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FootballMatchInfo extends AppCompatActivity {

    TextView txtHomeClubName;
    ImageView imgHomeClubName;
    TextView txtAwayClubName;
    ImageView imgAwayClubName;
    TextView tvDate, tvStadiumName, tvAddress, tvPhoneNumber, tvDescription, tvAmountsClub;
    Button btnSelectClub, btnRequest, btnBackToMatch, btnDeleteMatch;
    String stadiumName, match_id;

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    MatchService matchService = new MatchService();
    Map<String, MatchModel> mapMatchs = matchService.getMapMatchs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_match_info);

        Intent idMatchFromFragment = getIntent();
        match_id = idMatchFromFragment.getStringExtra("match_id");
        stadiumName = idMatchFromFragment.getStringExtra("stadium_name");

        connectView();
        showMatchInfo();
    }

    private void connectView() {
        txtHomeClubName = findViewById(R.id.txtMatchInfo_HomeClubName);
        txtAwayClubName = findViewById(R.id.tvMatchInfoAct_AwayClubName);
        imgHomeClubName = findViewById(R.id.imgMatchInfoAct_HomeClubAvt);
        imgAwayClubName = findViewById(R.id.imgMatchInfoAct_AwayClubAvt);
        tvDate = findViewById(R.id.tv_match_info_date);
        tvStadiumName = findViewById(R.id.txtMatchInfoAct_Stadium);
        tvAddress = findViewById(R.id.txtMatchInfo_address);
        tvPhoneNumber = findViewById(R.id.txtMatchCreateAct_PhoneNumber);
        tvDescription = findViewById(R.id.txtMatchCreateAct_Description);
        tvAmountsClub = findViewById(R.id.tv_amounts_club);
        btnSelectClub = findViewById(R.id.btnMatchInfoAct_ApplyMatch);
        btnRequest = findViewById(R.id.btn_request);
        btnBackToMatch = findViewById(R.id.btn_match_info_back);
        btnDeleteMatch = findViewById(R.id.btn_delete_match);
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


    }

    private void showMatchInfo() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("matchs");
        databaseReference.orderByKey().equalTo("-M80o8IiqppwwOrxDY9B").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                        tvDate.setText("sdfsdf");
//                        tvStadiumName.setText("sdfsdf");
//                        tvAddress.setText("sdfsdf");
//                        tvPhoneNumber.setText("sdfsdf");
//                        tvDescription.setText("sdfsdf");
//                        tvAmountsClub.setText("2" + " đội muốn tham gia trận đấu này");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
