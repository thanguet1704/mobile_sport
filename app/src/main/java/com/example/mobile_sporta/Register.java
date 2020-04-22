package com.example.mobile_sporta;

import android.os.Bundle;

import com.example.mobile_sporta.R;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Register extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnRegister;
    private EditText email, pass, rePass;
    private String mail, pw,rePw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    private void findId(){
        btnBack =  findViewById(R.id.back);
        btnRegister = findViewById(R.id.btnRegister);
        email = findViewById(R.id.register_email);
        pass = findViewById(R.id.register_pass);
        rePass = findViewById(R.id.register_re_pass);
    }
}
