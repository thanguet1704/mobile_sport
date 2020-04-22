package com.example.mobilesporta.menu;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Club extends AppCompatActivity {

    FloatingActionButton fabAddNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        fabAddNew = (FloatingActionButton) findViewById(R.id.fabClubAdd_AddNewClub);
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_new_club);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_club_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
