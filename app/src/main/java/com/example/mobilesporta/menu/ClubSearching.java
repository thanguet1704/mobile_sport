package com.example.mobilesporta.menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.R;

import java.util.ArrayList;

import static com.example.mobilesporta.R.*;

public class ClubSearching extends AppCompatActivity {

    ListView searchClub;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_club_searching);

        searchClub = findViewById(id.lvClubSearchingAct_SearchClub);

        ArrayList<String> listClub = new ArrayList<>();

        String a = "abc";
        String b = "bbc";
        String c = "bac";
        String d = "c";

        listClub.add(a);
        listClub.add(b);
        listClub.add(c);
        listClub.add(d);

        adapter = new ArrayAdapter<String>(
                ClubSearching.this,
                android.R.layout.simple_list_item_1,
                listClub
        );

        searchClub.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchview_club, menu);
        MenuItem item = menu.findItem(R.id.searchClub_Item);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
