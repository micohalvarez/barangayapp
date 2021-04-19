package com.release.barangayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewsFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
    }

    @Override
    public void onBackPressed() { }
}