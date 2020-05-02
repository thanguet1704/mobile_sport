package com.example.mobilesporta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.activity.account.EditAccount;
import com.example.mobilesporta.fragment.account.AccountFragment;
import com.example.mobilesporta.fragment.club.CLubFragment;
import com.example.mobilesporta.fragment.game.GameFragment;
import com.example.mobilesporta.fragment.news.NewsFragment;
import com.example.mobilesporta.fragment.stadium.StadiumFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Intent data = getIntent();
        if (data.getStringExtra("main") != null){
            bottomNav.setSelectedItemId(R.id.nav_stadium);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StadiumFragment()).commit();
        }else{
            bottomNav.setSelectedItemId(R.id.nav_account);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_news:
                    selectedFragment = new NewsFragment();
                    break;
                case R.id.nav_stadium:
                    selectedFragment = new StadiumFragment();
                    break;
                case R.id.nav_game:
                    selectedFragment = new GameFragment();
                    break;
                case R.id.nav_club:
                    selectedFragment = new CLubFragment();
                    break;
                case R.id.nav_account:
                    selectedFragment = new AccountFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}
