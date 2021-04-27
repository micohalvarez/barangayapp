package com.release.barangayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class NotificationsActivity extends AppCompatActivity {

    FloatingActionButton Addannouncement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Addannouncement = findViewById(R.id.AddAnnouncement);

        Addannouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Add Announcement


                //sample only
                Intent emergency=new Intent(NotificationsActivity.this, Emergency.class);
                startActivity(emergency);
            }
        });


    }
}