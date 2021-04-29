package com.release.barangayapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.release.barangayapp.R;

import java.util.ArrayList;

public class CreateAnnouncementActivity extends AppCompatActivity {

    private ArrayList<String> icons;

    ImageView iconType;
    EditText subjectSection,announcementDetail;
    Spinner editIcon;
    FloatingActionButton FABSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_createannouncement);

        subjectSection = findViewById(R.id.RegPersonnelCPass);
        editIcon = (Spinner) findViewById(R.id.spinnerEditIcon);
        iconType = findViewById(R.id.CAnnounceIconType);
        announcementDetail = findViewById(R.id.CAnnounceDetail);
        FABSend = findViewById(R.id.CAnnouncementSend);

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


        FABSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
