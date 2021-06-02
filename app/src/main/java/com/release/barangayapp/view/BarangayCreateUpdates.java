package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.release.barangayapp.R;
import com.release.barangayapp.model.UpdateReport;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.UpdateReportService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class BarangayCreateUpdates extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout AdmindrawerLayout;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    TextView createUpdateDateView;
    EditText confirmedUpdate, recoveredUpdate, deathUpdate;
    Button buttonUpdate;
    UpdateReportService updateReportService;
    UpdateReport updateReport;
    DatabaseReference reference;
    private AuthService updateAuthService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_create_updates);

        updateAuthService = new AuthService();
        updateAuthService.getUserDetails(value ->  {
            if(updateAuthService.getAuthUser() == null) {
                Intent homeIntent = new Intent(BarangayCreateUpdates.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("update_report");

        updateReport = new UpdateReport();
        updateReportService = new UpdateReportService();

        confirmedUpdate = findViewById(R.id.barangay_updates_confirmed);
        recoveredUpdate = findViewById(R.id.barangay_updates_recovered);
        deathUpdate = findViewById(R.id.barangay_updates_deaths);
        buttonUpdate = findViewById(R.id.barangay_Updates_button);




        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.updates_view);
        Admintoolbar = findViewById(R.id.summary_bar);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
        createUpdateDateView = findViewById(R.id.barangay_create_update_date_layout);
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        createUpdateDateView.setText(currentdate);

        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, AdmindrawerLayout, Admintoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AdmindrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        AdminnavigationView.setNavigationItemSelectedListener(this);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sumconfirmed = confirmedUpdate.getText().toString().trim();
                String sumrecovered = recoveredUpdate.getText().toString().trim();
                String sumdeaths = deathUpdate.getText().toString().trim();

                if (TextUtils.isEmpty(sumconfirmed))
                {
                    confirmedUpdate.setError("Please Enter Confirmed number Details");
                    return;
                }
                else if (TextUtils.isEmpty(sumrecovered))
                {
                    recoveredUpdate.setError("Please Enter Recovered number Details");
                    return;
                }
                else if (TextUtils.isEmpty(sumdeaths))
                {
                    deathUpdate.setError("Please Enter Death number Details");
                    return;
                }
                else
                {
                    createUpdate(updateAuthService.getAuthUser().getUid());
                    Toast.makeText(BarangayCreateUpdates.this, "Announcement Created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Reports.class));
                }
            }
        });
    }

    public void createUpdate(String userID)
    {
        updateReport.setConfirmed(confirmedUpdate.getText().toString().trim());
        updateReport.setRecovered(recoveredUpdate.getText().toString().trim());
        updateReport.setDeaths(deathUpdate.getText().toString().trim());
        updateReport.setUpdateDateView(createUpdateDateView.getText().toString().trim());
        updateReportService.saveData(updateReport,getApplicationContext());
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
                Intent home = new Intent(BarangayCreateUpdates.this, AdminMainMenu.class);
                startActivity(home);
                finish();
                break;
            case R.id.admin_profile:
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(BarangayCreateUpdates.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(BarangayCreateUpdates.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                updateAuthService.signOut();
                break;

        }
        AdmindrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}