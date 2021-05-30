package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.release.barangayapp.R;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.model.SummaryReport;
import com.release.barangayapp.service.AnnouncementService;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.SummaryReportService;

public class CreateSummary extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout AdmindrawerLayout;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    private AuthService authService;

    EditText Probable, Suspected, Confirmed;
    Button save;
    SummaryReportService summaryReportService;
    SummaryReport summaryReport;
    DatabaseReference reference;

    String probable, confirmed, suspect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_summary);


        authService = new AuthService();
        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(CreateSummary.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });



        reference = FirebaseDatabase.getInstance().getReference("summary_report");

        summaryReportService = new SummaryReportService();
        summaryReport = new SummaryReport();

        Probable = findViewById(R.id.summary_probable);
        Suspected = findViewById(R.id.summary_suspected);
        Confirmed = findViewById(R.id.summary_confirmed);
        save = findViewById(R.id.Summary_button);



        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.summary_view);
        Admintoolbar = findViewById(R.id.summary_bar);

        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, AdmindrawerLayout, Admintoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AdmindrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        AdminnavigationView.setNavigationItemSelectedListener(this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sumprobable = Probable.getText().toString().trim();
                String sumsuspected = Suspected.getText().toString().trim();
                String sumconfirmed = Confirmed.getText().toString().trim();

                if (TextUtils.isEmpty(sumprobable))
                {
                    Probable.setError("Please Enter Probable Details");
                    return;
                }
                else if (TextUtils.isEmpty(sumsuspected))
                {
                    Suspected.setError("Please Enter Suspected Details");
                    return;
                }
                else if (TextUtils.isEmpty(sumconfirmed))
                {
                    Confirmed.setError("Please Enter Confirmed Details");
                    return;
                }
                else
                {
                    createSummary(authService.getAuthUser().getUid());
                    Toast.makeText(CreateSummary.this, "Announcement Created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Reports.class));
                }
            }
        });
    }


    public void createSummary(String userId){


        //Set Objects
        summaryReport.setProbable(Probable.getText().toString().trim());
        summaryReport.setSuspect(Suspected.getText().toString().trim());
        summaryReport.setConfirmed(Confirmed.getText().toString().trim());
        summaryReportService.saveData(summaryReport,getApplicationContext());
    }

   /* public void update(View view){
        if(probableChanged() || suspectedChanged() || confirmedChanged()){
            Toast.makeText(this, "Data Has beed Updated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean confirmedChanged() {
        if(!probable.equals(Probable.getText().toString().trim())){
            reference.child(probable).child("probable").setValue(Probable.getText().toString().trim());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean suspectedChanged() {
        if(!confirmed.equals(Confirmed.getText().toString().trim())){
            reference.child(confirmed).child("confirmed").setValue(Confirmed.getText().toString().trim());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean probableChanged() {
    }*/


    @Override
    public void onBackPressed() {

        if (AdmindrawerLayout.isDrawerOpen((GravityCompat.START))) {

            AdmindrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.admin_home:
                Intent home = new Intent(CreateSummary.this, AdminMainMenu.class);
                startActivity(home);
                finish();
                break;
            case R.id.admin_profile:
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(CreateSummary.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(CreateSummary.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        AdmindrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}