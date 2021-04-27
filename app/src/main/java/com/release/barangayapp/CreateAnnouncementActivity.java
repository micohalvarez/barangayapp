package com.release.barangayapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CreateAnnouncementActivity extends AppCompatActivity {

    private ArrayList<String> icons;

    ImageView iconType, sendIcon;
    EditText subjectSection,announcementDetail;
    Spinner editIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_createannouncement);

        subjectSection = findViewById(R.id.RegPersonnelCPass);
        editIcon = (Spinner) findViewById(R.id.spinnerEditIcon);
        iconType = findViewById(R.id.CAnnounceIconType);
        sendIcon = findViewById(R.id.CAnnounceSendIcon);
        announcementDetail = findViewById(R.id.CAnnounceDetail);

        this.initializeDropdown();
        ArrayAdapter<String> iconAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.icons);
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        editIcon.setAdapter(iconAdapter);

        editIcon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    private void initializeDropdown(){
        this.icons = new ArrayList<>();
        //icon labels
        this.icons.add("Edit Icon");
        this.icons.add("Fire");
        this.icons.add("Weather");
        this.icons.add("Covid");
        this.icons.add("Crime");

    }
}
