package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.release.barangayapp.R;
import com.release.barangayapp.model.Emergency;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.EmergencyService;
import com.release.barangayapp.service.NotificationService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class EmergencyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout emergencyDrawyerLayout;
    private GridLayout emergencyMainGrid;
    private NavigationView emergencyNavigationView;
    private Toolbar emergencyToolBar;
    private AuthService authService;
    private NotificationService notificationService;
    private EmergencyService emergencyService;
    private Emergency emergency;
    private String message;
    private String address;
    private String date;
    private UserObject curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        authService = new AuthService();

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(EmergencyActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
            else{
                if(value != null)
                    curUser = value;
            }
        });


        emergencyDrawyerLayout = findViewById(R.id.Emergencydrawer_layout);
        emergencyNavigationView = findViewById(R.id.Emergencynav_view);
        emergencyToolBar = findViewById(R.id.Emergencytool_bar);

        emergencyNavigationView.bringToFront();
        setSupportActionBar(emergencyToolBar);
        emergencyMainGrid = findViewById(R.id.Emergency_mainGrid);
        setSingleEvent(emergencyMainGrid);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, emergencyDrawyerLayout, emergencyToolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        emergencyDrawyerLayout.addDrawerListener(toggle);
        toggle.syncState();
        emergencyNavigationView.setNavigationItemSelectedListener(this);




    }

    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0; i<mainGrid.getChildCount();i++)
        {
            CardView cardview = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(v -> {

                if(finalI == 0)
                {

                generateEmergency(1,"Help! " + curUser.getFullName() + " has a fire emergency at " + curUser.getAddress() + ". Please send help!");

                }
                else if (finalI == 1)
                {
                    generateEmergency(2,"Help! " + curUser.getFullName() + " has a medical emergency at " +  curUser.getAddress() + ". Please send help!");
                }
                else if (finalI == 2)
                {
                    generateEmergency(3,"Help! " + curUser.getFullName() + " has a crime emergency at " +  curUser.getAddress() + ". Please send help!");
                }
                else if (finalI == 3)
                {
                    generateEmergency(4,"Help! " + curUser.getFullName() + " has an accident emergency at " +  curUser.getAddress() + ". Please send help!");
                }

            });
        }
    }


    private void generateEmergency(int emergencyType, String message){
        notificationService = new NotificationService();
        emergencyService = new EmergencyService();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));

        emergency = new Emergency();
        emergency.setFinished(false);
        emergency.setUserId(curUser.getUserId());
        emergency.setType(emergencyType);
        if(emergencyType==1)
        {
            emergency.setTitle("Fire Emergency");
        }
        else if(emergencyType==2)
        {
            emergency.setTitle("Medical Emergency");
        }
        else if(emergencyType==3)
        {
            emergency.setTitle("Crime Emergency");
        }
        else if(emergencyType==4)
        {
            emergency.setTitle("Accident Emergency");
        }
        emergency.setPhonenumber(curUser.getPhonenumber());
        emergency.setCurrentDate(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
        emergency.setMessage(message);

        emergencyService.saveData(emergency);
        notificationService.sendNotif(resp-> {
            if(resp){
                startActivity(new Intent(getApplicationContext(), UserMainMenu.class));
                finish();
            }
            else{
                Toast.makeText( getApplicationContext(),"A server error has occurred.", Toast.LENGTH_SHORT).show();
            }
        },"Please send help!",message
        ,getApplicationContext());
    }

    @Override
    public void onBackPressed() {

        if (emergencyDrawyerLayout.isDrawerOpen((GravityCompat.START))) {

            emergencyDrawyerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_home:
                Intent home = new Intent(EmergencyActivity.this, UserMainMenu.class);
                startActivity(home);
                finish();
                break;
            case R.id.user_profile:
                Intent profile = new Intent(EmergencyActivity.this, ResidentProfile.class);
                startActivity(profile);
                finish();
                break;
            case R.id.user_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(EmergencyActivity.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        emergencyDrawyerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}