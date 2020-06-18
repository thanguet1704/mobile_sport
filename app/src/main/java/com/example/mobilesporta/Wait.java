package com.example.mobilesporta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Wait extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        imageView = findViewById(R.id.img_bg);
        imageView.animate().alpha(4000).setDuration(0);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Wait.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}
