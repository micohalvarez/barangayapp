package com.release.barangayapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAnnouncementActivity extends AppCompatActivity {

    ImageView iconType, sendIcon;
    EditText subjectSection,announcementDetail;
    TextView editIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_createannouncement);

        subjectSection = findViewById(R.id.CAnnounceSubject);
        editIcon = findViewById(R.id.CAnnounceEditIcon);
        iconType = findViewById(R.id.CAnnounceIconType);
        sendIcon = findViewById(R.id.CAnnounceSendIcon);
        announcementDetail = findViewById(R.id.CAnnounceDetail);

        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
