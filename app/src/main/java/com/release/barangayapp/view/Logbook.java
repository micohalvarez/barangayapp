package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.android.material.navigation.NavigationView;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

public class Logbook extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout AdmindrawerLayout;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    private AuthService authService;
    private Button Button_summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logbook);

        authService = new AuthService();
        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(Logbook.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });




        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.Loogbook_view);
        Admintoolbar = findViewById(R.id.Loogbook_bar);

        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, AdmindrawerLayout, Admintoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AdmindrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        AdminnavigationView.setNavigationItemSelectedListener(this);





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
                Intent home = new Intent(Logbook.this, AdminMainMenu.class);
                startActivity(home);
                finish();
                break;
            /*case R.id.admin_profile:
                break;*/
            case R.id.admin_register:
                Intent registerIntent = new Intent(Logbook.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(Logbook.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        AdmindrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}