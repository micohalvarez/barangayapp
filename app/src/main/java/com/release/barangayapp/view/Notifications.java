package com.release.barangayapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.release.barangayapp.NotificationsActivity;
import com.release.barangayapp.R;

public class Notifications extends AppCompatActivity {

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
                Intent emergency=new Intent(Notifications.this, Emergency.class);
                startActivity(emergency);
            }
        });
    }
}