package com.example.mobilesporta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnRegister;
    private EditText edtEmail, edtPass, edtrePass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findId();

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findId(){
        btnBack =  findViewById(R.id.back);
        btnRegister = findViewById(R.id.btnRegister);
        edtEmail = findViewById(R.id.register_email);
        edtPass = findViewById(R.id.register_pass);
        edtrePass = findViewById(R.id.register_re_pass);
    }

    private void register(){
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        String rePassword = edtrePass.getText().toString();
        if (email.length() == 0 || password.length() == 0 || rePassword.length() == 0){
            Toast.makeText(Register.this, "Nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        }else{
            if (password.equals(rePassword) == false){
                Toast.makeText(Register.this, "Nhập lại mật khẩu không đúng", Toast.LENGTH_LONG).show();
            }else{
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Register.this, "Email không hợp lệ hoặc mật khẩu lớn hơn 6 ký tự", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
    }
}
