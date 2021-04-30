package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.release.barangayapp.R;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.model.UserRegisterObject;
import com.release.barangayapp.service.AnnouncementService;
import com.release.barangayapp.service.AuthService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    UserRegisterObject userRegisterObject;
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
            }else
                {
                    userRegisterObject = value;

                }
        });
        subjectSection = findViewById(R.id.RegPersonnelCPass);
        editIcon = (Spinner) findViewById(R.id.spinnerEditIcon);
        iconType = findViewById(R.id.CAnnounceIconType);
        announcementDetail = findViewById(R.id.CAnnounceDetail);
        fabSend = findViewById(R.id.CAnnouncementSend);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
        Dateview = findViewById(R.id.ACurrentDate);
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Dateview.setText(currentdate);

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
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.notif_fire));
                        break;
                    }
                    case 2:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.notif_weather));
                        break;
                    }
                    case 3:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.notif_covid));
                        break;
                    }
                    case 4:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.notif_crime));
                        break;
                    }
                    case 5:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.notif_news));
                        break;
                    }
                    case 6:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.notif_med));
                        break;
                    }
                    case 7:
                    {
                        iconType.setImageDrawable(getResources().getDrawable(R.drawable.notif_accident));
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

                sendAnnouncement(userRegisterObject.);

            }
        });



    }

    public void sendAnnouncement(String userId){

        announcementService = new AnnouncementService();
        Announcement userAnnouncement = new Announcement();
        //Set Objects
        userAnnouncement.setTitle(subjectSection.getText().toString().trim());
        userAnnouncement.setContent(announcementDetail.getText().toString().trim());
        userAnnouncement.setCurrentDate(Dateview.getText().toString().trim());

       announcementService.saveData(userAnnouncement, userId);
    }

    private void initializeDropdown(){
        this.icons = new ArrayList<>();
        //icon labels
        this.icons.add("Edit Icon");
        this.icons.add("Fire");
        this.icons.add("Weather");
        this.icons.add("Covid");
        this.icons.add("Crime");
        this.icons.add("News");
        this.icons.add("Health");
        this.icons.add("Accident");

    }
}
