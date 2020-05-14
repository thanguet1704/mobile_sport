package com.example.mobilesporta.activity.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilesporta.R;

public class FootballMatchCreateNew extends AppCompatActivity {

    EditText edtStadium;
    EditText edtTimeAmount;
    EditText edtDate;
    EditText edtTime;
    EditText edtPhoneNumber;
    EditText edtDescription;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_match_create_new);

        connectView();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }

    private void saveMatch() {

    }
}
