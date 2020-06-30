package com.example.mobilesporta.fragment.game;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchInfo;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.MapConst;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ListView listView;

    TextView txtDate;
    TextView txtDate2;
    TextView txtTime, txtNone;
    ImageView imgNone;
    ItemFootballMatchAdapter itemFootballMatchAdapter;
    LinearLayout linearLayout, llNone;

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

        txtDate.setText("Lọc");
        txtDate2.setText("Đến");
        showListMatch();
        pickDate();
        pickDate2();
        // Inflate the layout for this fragment
        return view;
    }

    private void connectView(View view) {
        listView = (ListView) view.findViewById(R.id.lvSugesstion_Match_Fragment_ListMatch);
        txtDate = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterDateSearch);
        txtDate2 = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterDateSearch2);
        txtNone = view.findViewById(R.id.txt_none);
        imgNone = view.findViewById(R.id.img_none);
        linearLayout = view.findViewById(R.id.ll_filter);
        llNone = view.findViewById(R.id.ll_none);
    }

    private void clearData() {
        mapMatchModelsByDate.clear();
        listMatchByDate.clear();
        listMatchIdByDate.clear();
    }

    private void showListMatch() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchsByDate = mDatabase.orderByKey();
        matchsByDate.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    clearData();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        try {
                            String formattedString = sDF.format(calendar.getTime());

                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(formattedString);
                            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(match.getDate());

                            if (date1.compareTo(date2) <= 0 && !user.getUid().equals(match.getUser_created_id()) && match.getStatus().equals(MapConst.STATUS_MATCH_NONE)) {
                                listMatchByDate.add(match);
                                listMatchIdByDate.add(snapshot.getKey());
                                mapMatchModelsByDate.put(snapshot.getKey(), match);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (listMatchByDate.size() == 0){
                        txtDate.setVisibility(View.GONE);
                        llNone.setVisibility(View.VISIBLE);
                    }else{
                        linearLayout.setVisibility(View.VISIBLE);
                        llNone.setVisibility(View.GONE);
                    }
                    itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatchByDate, mapClubs);
                    listView.setAdapter(itemFootballMatchAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), FootballMatchInfo.class);
                            intent.putExtra("match_id", listMatchIdByDate.get(listMatchIdByDate.size() - 1- position));
                            startActivity(intent);
                        }
                    });
                    itemFootballMatchAdapter.notifyDataSetChanged();

                }else{
                    txtDate.setVisibility(View.GONE);
                    llNone.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

    private void pickDate() {

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_InputMethod, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        txtDate.setText(sDF.format(calendar.getTime()));

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
                        Query matchsByDate = mDatabase.orderByKey();

                        matchsByDate.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    clearData();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        MatchModel match = snapshot.getValue(MatchModel.class);
                                        try {
                                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDate.getText().toString());
                                            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(match.getDate());


                                            if (txtDate2.getText().toString().equals("Đến")) {
                                                if (date1.compareTo(date2) <= 0 && !user.getUid().equals(match.getUser_created_id()) && match.getStatus().equals(MapConst.STATUS_MATCH_NONE)) {
                                                    listMatchByDate.add(match);
                                                    listMatchIdByDate.add(snapshot.getKey());
                                                    mapMatchModelsByDate.put(snapshot.getKey(), match);
                                                }
                                            } else {
                                                Date date1t = new SimpleDateFormat("dd/MM/yyyy").parse(txtDate2.getText().toString());
                                                if (date1.compareTo(date2) <= 0 && date1t.compareTo(date2) >= 0 && !user.getUid().equals(match.getUser_created_id()) && match.getStatus().equals(MapConst.STATUS_MATCH_NONE)) {
                                                    listMatchByDate.add(match);
                                                    listMatchIdByDate.add(snapshot.getKey());
                                                    mapMatchModelsByDate.put(snapshot.getKey(), match);
                                                }
                                            }


                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (listMatchByDate.size() == 0){
                                        llNone.setVisibility(View.VISIBLE);
                                    }else{
                                        linearLayout.setVisibility(View.VISIBLE);
                                        llNone.setVisibility(View.GONE);
                                    }
                                    itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatchByDate, mapClubs);
                                    listView.setAdapter(itemFootballMatchAdapter);
                                    txtDate2.setVisibility(View.VISIBLE);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getActivity(), FootballMatchInfo.class);
                                            intent.putExtra("match_id", listMatchIdByDate.get(listMatchIdByDate.size() - 1- position));
                                            startActivity(intent);
                                        }
                                    });
                                }else{
                                    llNone.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                            }
                        });

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });
    }

    private void pickDate2() {

        txtDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_InputMethod, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        txtDate2.setText(sDF.format(calendar.getTime()));

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
                        Query matchsByDate = mDatabase.orderByKey();

                        matchsByDate.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    clearData();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        MatchModel match = snapshot.getValue(MatchModel.class);
                                        try {

                                            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(match.getDate());

                                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDate.getText().toString());

                                            Date date1t = new SimpleDateFormat("dd/MM/yyyy").parse(txtDate2.getText().toString());
                                            if (date1.compareTo(date2) <= 0 && date1t.compareTo(date2) >= 0 && !user.getUid().equals(match.getUser_created_id()) && match.getStatus().equals(MapConst.STATUS_MATCH_NONE)) {
                                                listMatchByDate.add(match);
                                                listMatchIdByDate.add(snapshot.getKey());
                                                mapMatchModelsByDate.put(snapshot.getKey(), match);
                                            }


                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatchByDate, mapClubs);
                                    listView.setAdapter(itemFootballMatchAdapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getActivity(), FootballMatchInfo.class);
                                            intent.putExtra("match_id", listMatchIdByDate.get(listMatchIdByDate.size() - 1- position));
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                            }
                        });

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });
    }
}
