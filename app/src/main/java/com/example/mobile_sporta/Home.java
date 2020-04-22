package com.example.mobile_sporta;

import android.os.Bundle;

import com.example.mobile_sporta.R;
import com.example.mobile_sporta.fragment.account.AccountFragment;
import com.example.mobile_sporta.fragment.club.CLubFragment;
import com.example.mobile_sporta.fragment.game.GameFragment;
import com.example.mobile_sporta.fragment.news.NewsFragment;
import com.example.mobile_sporta.fragment.stadium.StadiumFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_stadium);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StadiumFragment()).commit();
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
