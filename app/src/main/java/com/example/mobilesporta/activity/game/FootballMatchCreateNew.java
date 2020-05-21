package com.example.mobilesporta.activity.game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.club.ClubProfile;
import com.example.mobilesporta.data.MapConst;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.MatchModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

public class FootballMatchCreateNew extends AppCompatActivity {

    TextView edtStadium;
    TextView edtTimeAmount;
    TextView edtDate;
    TextView edtTime;
    EditText edtPhoneNumber;
    EditText edtDescription;
    Button btnSave;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView tvSelectClub;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_match_create_new);

        connectView();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        edtDate.setText(date.format(calendar.getTime()));
        edtTime.setText(time.format(calendar.getTime()));
        edtTimeAmount.setText("90");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballMatchCreateNew.this, Home.class);
                intent.putExtra("add_match", "true");
                startActivity(intent);
            }
        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });

        edtTimeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNumberPickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                createMatch();
            }
        });

        tvSelectClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FootballMatchCreateNew.this, "skjdfhs", Toast.LENGTH_LONG).show();
            }
        });

        edtStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballMatchCreateNew.this, SearchSelectStadiumForMatch.class);
                startActivity(intent);
            }
        });
    }

    private void connectView() {
        edtStadium = findViewById(R.id.edtMatchCreateAct_Stadium);
        edtTimeAmount = findViewById(R.id.edtMatchCreateAct_TimeAmount);
        edtDate = findViewById(R.id.edtMatchCreateAct_Date);
        edtTime = findViewById(R.id.edtMatchCreateAct_Time);
        edtPhoneNumber = findViewById(R.id.edtMatchCreateAct_PhoneNumber);
        edtDescription = findViewById(R.id.edtMatchCreateAct_Description);
        btnSave = findViewById(R.id.btnMatchCreateAct_Save);
        tvSelectClub = findViewById(R.id.tv_select_club);
        btnBack = findViewById(R.id.btn_back_create_match);
    }

    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void selectTime(){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                edtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, hour, min, true);

        timePickerDialog.show();
    }

    private void createMatch(){
        if (tvSelectClub.getText().toString().equals("") || edtStadium.getText().toString().equals("") || edtDate.getText().toString().equals("")
        || edtTime.getText().toString().equals("") || edtTimeAmount.getText().toString().equals("")
        || edtPhoneNumber.getText().toString().equals("") || edtDescription.getText().toString().equals("")){
            Toast.makeText(FootballMatchCreateNew.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else{
            String club_home_id = "-M7O5doGhyRqIeq98zvP";
            String club_away_id = "";
            String user_created_id = user.getUid();
            String stadium_id = "lskfjgljsdfg";
            String date = edtDate.getText().toString();
            String time = edtTime.getText().toString();
            Integer time_amount = Integer.parseInt(edtTimeAmount.getText().toString());

            MapConst mapConst = new MapConst();

            String status = mapConst.STATUS_MATCH_MAP.get("NONE");
            String description = edtDescription.getText().toString();
            String phone_number = edtPhoneNumber.getText().toString();
            MatchModel matchModel = new MatchModel(club_home_id, club_away_id, user_created_id, stadium_id, date, time, time_amount, status, description, phone_number);

            MatchService matchService = new MatchService();
            matchService.addMatch(matchModel);
            Intent intent = new Intent(FootballMatchCreateNew.this, FootballMatchInfo.class);
            intent.putExtra("match_id", "sdfd");
            startActivity(intent);
        }

    }

    private void openNumberPickerDialog(){
        View v1 = getLayoutInflater().inflate(R.layout.dialog_number_picker, null);

        final NumberPicker picker = (NumberPicker) v1.findViewById(R.id.np_time_amout);
        picker.setMinValue(60);
        picker.setMaxValue(120);
        picker.setWrapSelectorWheel(false);

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setView (v1);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int pickedValue = picker.getValue();
                edtTimeAmount.setText(Integer.toString(pickedValue));
                return;
            } });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            } });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
