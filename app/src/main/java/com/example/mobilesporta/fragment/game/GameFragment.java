package com.example.mobilesporta.fragment.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchCreateNew;
import com.example.mobilesporta.adapter.FootballMatchAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class GameFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem sugesstionsTab, myTab;
    public PagerAdapter pagerAdapter;
    private FloatingActionButton btnCreateMatch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.FootballMatchAct_TabLayout);
        sugesstionsTab = (TabItem) view.findViewById(R.id.FootballMatchAct_SuggestionsFbMatchTab);
        myTab = (TabItem) view.findViewById(R.id.FootballMatchAct_MyFbMatchTab);
        btnCreateMatch = view.findViewById(R.id.create_match);
        viewPager = view.findViewById(R.id.FootballMatchAct_ViewPager);

        pagerAdapter = new FootballMatchAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        btnCreateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FootballMatchCreateNew.class));
            }
        });

        return view;
    }
}
