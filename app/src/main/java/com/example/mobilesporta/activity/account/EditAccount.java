package com.example.mobilesporta.activity.account;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilesporta.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditAccount extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findId();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        edtEmail.setText(user.getEmail());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });
    }

    private void findId(){
        edtUsername = findViewById(R.id.edit_name);
        edtEmail = findViewById(R.id.edit_email);
        edtPassword = findViewById(R.id.edit_pass);
        edtConfirmPassword= findViewById(R.id.edit_confirm_pass);
        btnUpdate = findViewById(R.id.btn_update);
    }

    private void updateAccount(){
        final String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String username = edtUsername.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (username.length() == 0){
            if (user.getDisplayName().length() == 0){
                username = "NoName";
            }else{
                username = user.getDisplayName();
            }
        }else{
            username = edtUsername.getText().toString();
        }

        if (password.length() != 0 && confirmPassword.length() != 0){
            if (password.equals(confirmPassword) == false){
                Toast.makeText(EditAccount.this, "Nhập lại mật khẩu không đúng", Toast.LENGTH_LONG).show();
            }else{
                user.updatePassword(password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("success", password);
                            }
                        });


                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("success", "succcess full");
                                }
                            }
                        });

                startActivity(new Intent(EditAccount.this, Home.class));
            }
        }else{

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("success", "succcess full");
                            }
                        }
                    });

            startActivity(new Intent(EditAccount.this, Home.class));
        }
    }
}
