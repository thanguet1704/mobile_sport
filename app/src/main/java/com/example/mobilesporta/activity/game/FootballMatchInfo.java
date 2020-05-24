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

import com.example.mobilesporta.Home;
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
    String stadiumName, stadiumAddress;

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    MatchService matchService = new MatchService();
    Map<String, MatchModel> mapMatchs = matchService.getMapMatchs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_match_info);

        Intent intent = getIntent();
        final String match_id = intent.getStringExtra("match_id");
        stadiumName = intent.getStringExtra("stadium_name");
        stadiumAddress = intent.getStringExtra("stadium_address");

        connectView();
        showMatchInfo(match_id);

        btnBackToMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballMatchInfo.this, Home.class);
                intent.putExtra("add_match", "true");
                startActivity(intent);
            }
        });

        btnDeleteMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMatch(match_id);
            }
        });
    }

    private void connectView() {
        txtHomeClubName = findViewById(R.id.txtMatchInfo_HomeClubName);
        txtAwayClubName = findViewById(R.id.tvMatchInfoAct_AwayClubName);
        imgHomeClubName = findViewById(R.id.imgMatchInfoAct_HomeClubAvt);
        imgAwayClubName = findViewById(R.id.imgMatchInfoAct_AwayClubAvt);
        tvDate = findViewById(R.id.tv_match_info_date);
        tvStadiumName = findViewById(R.id.txtMatchInfoAct_Stadium);
        tvAddress = findViewById(R.id.txtMatchInfo_address);
        tvPhoneNumber = findViewById(R.id.txtMatchInfoAct_PhoneNumber);
        tvDescription = findViewById(R.id.txtMatchInfoAct_Description);
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

    private  void setViewClub(MatchModel matchModel) {

//        Log.e("s", String.valueOf(mapClubs.size()));
//        Log.e("ss", String.valueOf(mapMatchs.size()));
//
//        if (!matchModel.getClub_home_id().equals("")) {
//            ClubModel homeClubModel = getClubById(matchModel.getClub_home_id(), mapClubs);
//            Log.e("s", homeClubModel.getImage());
//            txtHomeClubName.setText(homeClubModel.getClub_name());
//            Picasso.get().load(homeClubModel.getImage()).into(imgHomeClubName);
//        }
//        else {
//            txtHomeClubName.setText("???");
//        }
//
//
//        if( !matchModel.getClub_away_id().equals("")) {
//            ClubModel awayClubModel = getClubById(matchModel.getClub_away_id(), mapClubs);
//            txtAwayClubName.setText(awayClubModel.getClub_name());
//            Picasso.get().load(awayClubModel.getImage()).into(imgAwayClubName);
//        }
//        else {
//            txtAwayClubName.setText("???");
//        }
//


//        Picasso.get().load(awayClub.getImage()).into(imgAwayClub);

    }

    private void showMatchInfo(String match_id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("matchs");
        databaseReference.orderByKey().equalTo(match_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        tvDate.setText(matchModel.getDate() + " từ " + matchModel.getTime());
                        tvStadiumName.setText(stadiumName);
                        tvAddress.setText(stadiumAddress);
                        tvPhoneNumber.setText(matchModel.getPhone_number());
                        tvDescription.setText(matchModel.getDescription());
                        tvAmountsClub.setText("0" + " đội muốn tham gia trận đấu này");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void deleteMatch(String matchId){
        MatchService matchService = new MatchService();
        matchService.deleteMatch(matchId);
        Intent intent = new Intent(FootballMatchInfo.this, Home.class);
        intent.putExtra("add_match", "true");
        startActivity(intent);
    }
}
