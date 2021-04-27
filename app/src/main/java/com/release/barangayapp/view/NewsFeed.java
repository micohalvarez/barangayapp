package com.release.barangayapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.release.barangayapp.R;

public class NewsFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
    }

    @Override
    public void onBackPressed() { }
}