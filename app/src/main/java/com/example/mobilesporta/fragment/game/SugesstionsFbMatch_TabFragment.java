package com.example.mobilesporta.fragment.game;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugesstionsFbMatch_TabFragment extends Fragment {

    MatchService matchService = new MatchService();
    List<MatchModel> listMatch = matchService.getListMatch();

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    ListView listView;

    TextView txtDate;
    TextView txtTime;

    public SugesstionsFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sugesstions_fb_match__tab, container, false);
        connectView(view);

        pickTime();
        pickDate();
        showListMatch();
        // Inflate the layout for this fragment
        return view;
    }

    private void connectView(View view) {
        listView = (ListView) view.findViewById(R.id.lvSugesstion_Match_Fragment_ListMatch);
        txtTime = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterTimeSearch);
        txtDate = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterDateSearch);
    }

    private void showListMatch() {
        ItemFootballMatchAdapter itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatch, mapClubs);
        listView.setAdapter(itemFootballMatchAdapter);
    }

    private void pickTime() {
        final Calendar calendar = Calendar.getInstance();
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
        final Calendar calendar = Calendar.getInstance();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat sDF = new SimpleDateFormat("dd/MM/yyy");
                        txtDate.setText(sDF.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
    }
}
