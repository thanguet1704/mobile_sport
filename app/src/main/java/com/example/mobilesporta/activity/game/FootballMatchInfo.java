package com.example.mobilesporta.activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.R;

public class FootballMatchInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_football_match_info);

        Intent intent = getIntent();
        String matchId = intent.getStringExtra("match_id");
    }
}
