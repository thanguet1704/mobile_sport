package com.example.mobilesporta.activity.game;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobilesporta.adapter.ItemStadiumForMatchAdapter;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.StadiumModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobilesporta.R;

import java.util.ArrayList;

public class SearchSelectStadiumForMatch extends AppCompatActivity {

    EditText edtSearch;
    Button btnCancel;
    ListView lvStadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_select_stadium_for_match);

        map();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSelectStadiumForMatch.this, FootballMatchCreateNew.class);
                startActivity(intent);
            }
        });

        ArrayList<StadiumModel> listStadium = new ArrayList<>();
        StadiumModel stadium = new StadiumModel();
        listStadium.add(stadium);

        ItemStadiumForMatchAdapter itemStadiumForMatchAdapter = new ItemStadiumForMatchAdapter(SearchSelectStadiumForMatch.this, R.layout.item_stadium_for_match, listStadium);
        lvStadium.setAdapter(itemStadiumForMatchAdapter);

        lvStadium.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchSelectStadiumForMatch.this, position + "" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void map(){
        edtSearch = findViewById(R.id.edt_search_stadium_for_match);
        btnCancel = findViewById(R.id.btn_cancel_search_stadium_for_match);
        lvStadium = findViewById(R.id.lv_stadium_for_match);
    }

}
