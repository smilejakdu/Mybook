package com.example.cns09.study;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.cns09.study.databinding.ActivityKyoboWebBinding;

public class KyoboWebActivity extends AppCompatActivity {
    private ActivityKyoboWebBinding binding;
    WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kyobo_web);

        setSupportActionBar(binding.tbBack);
        setTitle("Mybook");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        Log.d("qwe", "onCreate: " + title);

        binding.wvShow.loadUrl("https://search.kyobobook.co.kr/web/search?vPstrKeyWord=" + title + "&orderClick=LAG");
        webSettings = binding.wvShow.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.wvShow.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
