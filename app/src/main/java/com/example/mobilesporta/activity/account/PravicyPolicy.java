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

public class PravicyPolicy extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pravicy_policy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.wv_policy);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.sporta.vn/pages/chinh-sach-bao-mat");
    }

}
