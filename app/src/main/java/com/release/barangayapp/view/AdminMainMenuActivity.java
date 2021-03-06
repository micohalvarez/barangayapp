package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

public class AdminMainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout AdmindrawerLayout;
    GridLayout AdminmainGrid;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    private AuthService authService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        authService = new AuthService();
        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(AdminMainMenuActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });

        AdminmainGrid = findViewById(R.id.Admin_mainGrid);
        setSingleEvent(AdminmainGrid);


        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.Adminnav_view);
        Admintoolbar = findViewById(R.id.Admintool_bar);

        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

        //hide or show items
//        Menu menu = AdminnavigationView.getMenu();
//        menu.findItem(R.id.admin_logout).setVisible(false);
//        menu.findItem(R.id.admin_profile).setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, AdmindrawerLayout, Admintoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AdmindrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        AdminnavigationView.setNavigationItemSelectedListener(this);


    }

    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0; i<mainGrid.getChildCount();i++)
        {
            CardView cardview = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(v -> {

                if(finalI == 0)
                {
                    Intent notification=new Intent(AdminMainMenuActivity.this, NotificationsActivity.class);
                    startActivity(notification);

                }
                else if (finalI == 1)
                {
                    Intent reports=new Intent(AdminMainMenuActivity.this, Reports.class);
                    startActivity(reports);

                }
                else if (finalI == 2)
                {
                    Intent loogbook=new Intent(AdminMainMenuActivity.this, LogbookActivity.class);
                    startActivity(loogbook);
                }
                else if (finalI == 3)
                {
                    Intent adminchatsupport=new Intent(AdminMainMenuActivity.this, BarangayChatSupportActivity.class);
                    startActivity(adminchatsupport);
                }

            });
        }
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
                Intent home = new Intent(AdminMainMenuActivity.this, AdminMainMenuActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.admin_profile:
                Intent profile = new Intent(AdminMainMenuActivity.this, BarangayProfileActivity.class);
                startActivity(profile);
                finish();
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(AdminMainMenuActivity.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(AdminMainMenuActivity.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        AdmindrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
