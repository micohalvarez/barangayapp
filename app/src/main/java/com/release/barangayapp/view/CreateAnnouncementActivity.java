package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.release.barangayapp.R;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.service.AnnouncementService;
import com.release.barangayapp.service.AuthService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateAnnouncementActivity extends AppCompatActivity {

    private ArrayList<String> icons;

    TextView Dateview;
    ImageView iconType;
    EditText subjectSection,announcementDetail;
    Spinner editIcon;
    FloatingActionButton fabSend;
    private AuthService authService;
    AnnouncementService announcementService;
    Announcement userAnnouncement;
    int iconValue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_createannouncement);

        authService = new AuthService();
        authService.getUserDetails(value -> {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(CreateAnnouncementActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });
        subjectSection = findViewById(R.id.RegPersonnelCPass);
        editIcon = (Spinner) findViewById(R.id.spinnerEditIcon);
        iconType = findViewById(R.id.CAnnounceIconType);
        announcementDetail = findViewById(R.id.CAnnounceDetail);
        fabSend = findViewById(R.id.CAnnouncementSend);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
        Dateview = findViewById(R.id.ACurrentDate);
        String currentdate = DateFormat.getDateInstance().format(calendar.getTime());
        Dateview.setText(currentdate);

        announcementService = new AnnouncementService();
        userAnnouncement = new Announcement();

        this.initializeDropdown();
        ArrayAdapter<String> iconAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.icons);
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        editIcon.setAdapter(iconAdapter);

        editIcon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 1:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.ann_fire));
                        iconValue = 0;
                        break;
                    }
                    case 2:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.ann_weather));
                        iconValue = 1;
                        break;
                    }
                    case 3:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.ann_covid));
                        iconValue = 2;
                        break;
                    }
                    case 4:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.ann_crime));
                        iconValue = 3;
                        break;
                    }
                    case 5:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.ann_news_a));
                        iconValue = 4;
                        break;
                    }
                    case 6:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.ann_health_b));
                        iconValue = 5;
                        break;
                    }
                    case 7:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.ann_accident_a));
                        iconValue = 6;
                        break;
                    }
                    default:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.defaulticon));
                        break;
                    }




                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = subjectSection.getText().toString().trim();
                String detail = announcementDetail.getText().toString().trim();


                if (TextUtils.isEmpty(subject))
                {
                    subjectSection.setError("Must have a Subject");
                    return;
                }
                else if (TextUtils.isEmpty(detail))
                {
                    announcementDetail.setError("Must have an Announcement Detail");
                    return;
                }
                else if (editIcon.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(CreateAnnouncementActivity.this, "Must Select an Icon", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendAnnouncement(authService.getAuthUser().getUid());
                    Toast.makeText(CreateAnnouncementActivity.this, "Announcement Created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),NotificationsActivity.class));
                }

            }
        });



    }

    public void sendAnnouncement(String userId){


        //Set Objects
        userAnnouncement.setTitle(subjectSection.getText().toString().trim());
        userAnnouncement.setContent(announcementDetail.getText().toString().trim());
        userAnnouncement.setCurrentDate(Dateview.getText().toString().trim());
        userAnnouncement.setIconValue(iconValue);
        announcementService.saveData(userAnnouncement);
    }

    private void initializeDropdown(){
        this.icons = new ArrayList<>();
        //icon labels
        this.icons.add(" -----Select Icon-----");
        this.icons.add("       Fire Report");
        this.icons.add("  Weather Report");
        this.icons.add("     Covid Report");
        this.icons.add("     Crime Report");
        this.icons.add("     News Report");
        this.icons.add("    Health Report");
        this.icons.add("  Accident Report");

    }
}
