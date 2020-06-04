package com.example.mobilesporta.activity.account;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mobilesporta.R;

public class Recommend extends AppCompatActivity {

    WebView wvRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wvRecommend = findViewById(R.id.wv_gioithieu);
        wvRecommend.setWebViewClient(new WebViewClient());

        wvRecommend.loadUrl("https://www.sporta.vn/pages/about-us");
    }

}
