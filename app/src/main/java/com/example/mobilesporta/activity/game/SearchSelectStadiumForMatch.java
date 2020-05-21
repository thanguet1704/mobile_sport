package com.example.mobilesporta.activity.game;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobilesporta.adapter.ItemStadiumForMatchAdapter;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.StadiumCommentModel;
import com.example.mobilesporta.model.StadiumModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobilesporta.R;

import java.util.ArrayList;
import java.util.List;

public class SearchSelectStadiumForMatch extends AppCompatActivity {

    EditText edtSearch;
    Button btnCancel;
    ListView lvStadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_select_stadium_for_match);

        Intent intent = getIntent();
        intent.getStringExtra("match_id");

        map();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSelectStadiumForMatch.this, FootballMatchCreateNew.class);
                startActivity(intent);
            }
        });

        final ArrayList<StadiumModel> listStadium = new ArrayList<>();

        StadiumCommentModel stadiumComment = new StadiumCommentModel("sads", "sdsd");
        ArrayList<StadiumCommentModel> listStadiumComment = new ArrayList<>();
        listStadiumComment.add(stadiumComment);

        StadiumModel stadium = new StadiumModel();
        stadium.setStadium_name("sfdsdf");
        stadium.setDescription("asdas");
        stadium.setAddress("sdfsdf");
        stadium.setAmount(12312);
        stadium.setCost(21012);
        stadium.setImage("sdfsdf");
        stadium.setPhone_number("23123");
        stadium.setTime_open("12312");
        stadium.setTime_close("sdfsdf");
        stadium.setLocation_x("2312");
        stadium.setLocation_y("12321");
        stadium.setListComments(listStadiumComment);
        stadium.setZone("ssdfsdf");
        listStadium.add(stadium);

        ItemStadiumForMatchAdapter itemStadiumForMatchAdapter = new ItemStadiumForMatchAdapter(SearchSelectStadiumForMatch.this, R.layout.item_stadium_for_match, listStadium);
        lvStadium.setAdapter(itemStadiumForMatchAdapter);

        lvStadium.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("id", listStadium.get(position).getStadium_name() + "");
                Intent intent = new Intent();
                intent.putExtra("stadium_id", listStadium.get(position).getStadium_name());
                intent.putExtra("stadium_name", listStadium.get(position).getStadium_name());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void map(){
        edtSearch = findViewById(R.id.edt_search_stadium_for_match);
        btnCancel = findViewById(R.id.btn_cancel_search_stadium_for_match);
        lvStadium = findViewById(R.id.lv_stadium_for_match);
    }

}
