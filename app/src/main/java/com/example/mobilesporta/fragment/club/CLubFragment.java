package com.example.mobilesporta.fragment.club;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.menu.ClubSearching;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CLubFragment extends Fragment {

    FloatingActionButton fabAddNew;
    Button btnSearchClub;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_club, container, false);

        fabAddNew = (FloatingActionButton) view.findViewById(R.id.fabClubAdd_AddNewClub);
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        btnSearchClub = view.findViewById(R.id.btn_search_club);
        btnSearchClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClubSearching.class));
            }
        });
        return view;
    }

    public void openDialog() {
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_add_new_club);
        dialog.show();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_club_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //click item menu
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.menu.search_club_menu).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }
}
