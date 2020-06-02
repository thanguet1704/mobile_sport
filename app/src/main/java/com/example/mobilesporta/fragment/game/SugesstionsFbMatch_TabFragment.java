package com.example.mobilesporta.fragment.game;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchInfo;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugesstionsFbMatch_TabFragment extends Fragment {

    MatchService matchService = new MatchService();
    List<MatchModel> listMatch = matchService.getListMatch();
    List<String> listMatchId = matchService.getListMatchId();
    List<String> listMatchIdByDate = new ArrayList<>();

    List<MatchModel> listMatchByDate = new ArrayList<>();
    Map<String, MatchModel> mapMatchModelsByDate = new HashMap<>();

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    ListView listView;

    TextView txtDate;
    TextView txtTime;

    ItemFootballMatchAdapter itemFootballMatchAdapter;

    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat sDF = new SimpleDateFormat("dd/MM/yyy");

    public SugesstionsFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sugesstions_fb_match__tab, container, false);
        connectView(view);

        init();
        pickTime();
        pickDate();
        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
        txtDate.setText(sDF.format(calendar.getTime()));
        getDataByDate();
        showListMatch();
    }

    private void connectView(View view) {
        listView = (ListView) view.findViewById(R.id.lvSugesstion_Match_Fragment_ListMatch);
        txtTime = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterTimeSearch);
        txtDate = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterDateSearch);
    }

    private void getDataByDate() {
        mapMatchModelsByDate = matchService.getMapMatchByDateTime(txtDate.getText().toString());
        listMatchByDate = matchService.getListMatchByDateTime(txtDate.getText().toString());
        listMatchIdByDate = matchService.getListMatchIdByDate(txtDate.getText().toString());
    }

    private void clearData() {
        mapMatchModelsByDate.clear();
        listMatchByDate.clear();
        listMatchIdByDate.clear();
    }

    private void showListMatch() {

        itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatchByDate, mapClubs);
        listView.setAdapter(itemFootballMatchAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FootballMatchInfo.class);
                intent.putExtra("match_id", listMatchIdByDate.get(position));
                startActivity(intent);
            }
        });
    }


    private void pickTime() {
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
    }

    private void pickDate() {

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        txtDate.setText(sDF.format(calendar.getTime()));

//                        clearData();
//                        mapMatchModelsByDate.clear();
//                        mapMatchModelsByDate.putAll(matchService.getMapMatchByDateTime(txtDate.getText().toString()));
//                        mapMatchModelsByDate = matchService.getMapMatchByDateTime(txtDate.getText().toString());
//                        listMatchByDate.clear();
//                        listMatchByDate.addAll(matchService.getListMatchByDateTime(txtDate.getText().toString()));
//
//                        listMatchIdByDate.clear();
//                        listMatchIdByDate.addAll(matchService.getListMatchIdByDate(txtDate.getText().toString()));


                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
                        Query matchsByDate = mDatabase.orderByKey();

                        matchsByDate.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    clearData();
                                    Log.e("clear : ", "here");
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        MatchModel match = snapshot.getValue(MatchModel.class);
                                        try {

                                            Log.e("logic : ", "here");
                                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDate.getText().toString());
                                            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(match.getDate());

                                            if (date1.compareTo(date2) == 0) {
                                                listMatchByDate.add(match);
                                                listMatchIdByDate.add(snapshot.getKey());
                                                mapMatchModelsByDate.put(snapshot.getKey(), match);
                                                Log.e("test sizze", String.valueOf(listMatchByDate.size()));
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            Log.e("e : ", e.toString());
                                        }
                                    }
                                    Log.e("msg1A", String.valueOf(mapMatchModelsByDate.size()));
                                    Log.e("msg2A", String.valueOf(listMatchByDate.size()));
                                    Log.e("msg3A", String.valueOf(listMatchIdByDate.size()));
                                    itemFootballMatchAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                            }
                        });



                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));


//                showListMatch();
                datePickerDialog.show();
            }

        });
    }


}
