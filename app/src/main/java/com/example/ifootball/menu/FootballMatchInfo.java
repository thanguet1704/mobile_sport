package com.example.ifootball.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ifootball.R;

public class FootballMatchInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_football_match_info);
    }
}
