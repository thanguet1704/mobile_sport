package com.example.mobilesporta.activity.game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilesporta.R;
import com.example.mobilesporta.data.MapConst;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;

public class FootballMatchCreateNew extends AppCompatActivity {

    EditText edtStadium;
    EditText edtTimeAmount;
    EditText edtDate;
    EditText edtTime;
    EditText edtPhoneNumber;
    EditText edtDescription;
    Button btnSave;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_match_create_new);

        connectView();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String club_home_id = "-M7O5doGhyRqIeq98zvP";
                String club_away_id = "";
                String user_created_id = user.getUid();
                String stadium_id = "lskfjgljsdfg";
                String date = edtDate.getText().toString();
                String time = edtTime.getText().toString();
                Integer time_amount = Integer.parseInt(edtTimeAmount.getText().toString());

                MapConst mapConst = new MapConst();

                String status = mapConst.STATUS_MATCH_MAP.get("NONE");
                String description = edtDescription.getText().toString();
                String phone_number = edtPhoneNumber.getText().toString();
                MatchModel matchModel = new MatchModel(club_home_id, club_away_id, user_created_id, stadium_id, date, time, time_amount, status, description, phone_number);

                MatchService matchService = new MatchService();
                matchService.addMatch(matchModel);
                Intent intent = new Intent(FootballMatchCreateNew.this, FootballMatchInfo.class);
                intent.putExtra("match_id", "sdfd");
                startActivity(intent);
            }
        });
    }

    private void connectView() {
        edtStadium = findViewById(R.id.edtMatchCreateAct_Stadium);
        edtTimeAmount = findViewById(R.id.edtMatchCreateAct_TimeAmount);
        edtDate = findViewById(R.id.edtMatchCreateAct_Date);
        edtTime = findViewById(R.id.edtMatchCreateAct_Time);
        edtPhoneNumber = findViewById(R.id.edtMatchCreateAct_PhoneNumber);
        edtDescription = findViewById(R.id.edtMatchCreateAct_Description);
        btnSave = findViewById(R.id.btnMatchCreateAct_Save);
    }

    private void saveMatch() {

    }
}
