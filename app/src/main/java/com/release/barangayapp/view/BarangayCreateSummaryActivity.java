package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.release.barangayapp.R;
import com.release.barangayapp.model.SummaryReport;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.SummaryReportService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class BarangayCreateSummaryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout AdmindrawerLayout;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    private AuthService summaryAuthService;

    EditText Probable, Suspected, Confirmed;
    Button save;
    SummaryReportService summaryReportService;
    SummaryReport summaryReport;
    DatabaseReference reference;
    TextView summaryDateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_create_summary);


        summaryAuthService = new AuthService();
        summaryAuthService.getUserDetails(value ->  {
            if(summaryAuthService.getAuthUser() == null) {
                Intent homeIntent = new Intent(BarangayCreateSummaryActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });



        reference = FirebaseDatabase.getInstance().getReference("summary_report");

        summaryReportService = new SummaryReportService();
        summaryReport = new SummaryReport();

        Probable = findViewById(R.id.barangay_create_summary_probable);
        Suspected = findViewById(R.id.barangay_create_summary_suspected);
        Confirmed = findViewById(R.id.barangay_create_summary_confirmed);
        save = findViewById(R.id.barangay_Summary_button);



        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.summary_view);
        Admintoolbar = findViewById(R.id.summary_bar);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
        summaryDateView = findViewById(R.id.barangay_create_summary_date_layout);
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        summaryDateView.setText(currentdate);

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
                    createSummary(summaryAuthService.getAuthUser().getUid());
                    Toast.makeText(BarangayCreateSummaryActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
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
        summaryReport.setSummaryDateView(summaryDateView.getText().toString().trim());
        summaryReportService.saveData(summaryReport,getApplicationContext());
    }
    


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
                Intent home = new Intent(BarangayCreateSummaryActivity.this, AdminMainMenuActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.admin_profile:
                Intent profile = new Intent(BarangayCreateSummaryActivity.this, BarangayProfileActivity.class);
                startActivity(profile);
                finish();
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(BarangayCreateSummaryActivity.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(BarangayCreateSummaryActivity.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                summaryAuthService.signOut();
                break;

        }
        AdmindrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}