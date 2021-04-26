package com.release.barangayapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;

import com.google.android.material.navigation.NavigationView;
import com.release.barangayapp.service.AuthService;

public class AdminMainMenu extends AppCompatActivity {

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
                Intent homeIntent = new Intent(AdminMainMenu.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });

        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.Adminnav_view);
        Admintoolbar = findViewById(R.id.Admintool_bar);


        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, AdmindrawerLayout, Admintoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AdmindrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0; i<mainGrid.getChildCount();i++)
        {
            CardView cardview = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(v -> {

                if(finalI == 0)
                {
                    Intent notification=new Intent(AdminMainMenu.this, Notifications.class);
                    startActivity(notification);

                }
                else if (finalI == 1)
                {
                    Intent reports=new Intent(AdminMainMenu.this, Reports.class);
                    startActivity(reports);

                }
                else if (finalI == 2)
                {
                    Intent loogbook=new Intent(AdminMainMenu.this, Logbook.class);
                    startActivity(loogbook);
                }
                else if (finalI == 3)
                {
                    Intent adminchatsupport=new Intent(AdminMainMenu.this, AdminChatSupport.class);
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
}