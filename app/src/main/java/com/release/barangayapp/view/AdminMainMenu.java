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
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

public class AdminMainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout AdmindrawerLayout;
    GridLayout AdminmainGrid;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    private AuthService authService;
    private FirebaseAuth LogoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        authService = new AuthService();
        LogoutAuth = FirebaseAuth.getInstance();
        authService.getUserDetails(value ->  {

            if(authService.getAuthUser() == null && value == null) {
                Intent homeIntent = new Intent(AdminMainMenu.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });

        AdminmainGrid = findViewById(R.id.Admin_mainGrid);
        setSingleEvent(AdminmainGrid);


        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.Adminnav_view);
        AdminnavigationView.setNavigationItemSelectedListener(this);
        Admintoolbar = findViewById(R.id.Admintool_bar);
        //Test

        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

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
                    Intent notification=new Intent(AdminMainMenu.this, NotificationsActivity.class);
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.admin_profile:
                break;
                case R.id.admin_register:
                    break;
            case R.id.admin_settings:
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                LogoutAuth.signOut();
                break;

        }
        return true;
    }
}