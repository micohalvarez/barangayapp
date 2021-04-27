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

public class Emergency extends AppCompatActivity {
    DrawerLayout EmergencydrawerLayout;
    GridLayout EmergencymainGrid;
    NavigationView EmergencynavigationView;
    Toolbar Emergencytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        setSingleEvent(EmergencymainGrid);

        EmergencydrawerLayout = findViewById(R.id.Emergencydrawer_layout);
        EmergencynavigationView = findViewById(R.id.Emergencynav_view);
        Emergencytoolbar = findViewById(R.id.Emergencytool_bar);

        EmergencynavigationView.bringToFront();
        setSupportActionBar(Emergencytoolbar);

        EmergencymainGrid = findViewById(R.id.Emergency_mainGrid);
        setSingleEvent(EmergencymainGrid);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, EmergencydrawerLayout, Emergencytoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        EmergencydrawerLayout.addDrawerListener(toggle);
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
                    Intent notification=new Intent(Emergency.this, NotificationsActivity.class);
                    startActivity(notification);

                }
                else if (finalI == 1)
                {
                    Intent reports=new Intent(Emergency.this, Reports.class);
                    startActivity(reports);

                }
                else if (finalI == 2)
                {
                    Intent loogbook=new Intent(Emergency.this, Logbook.class);
                    startActivity(loogbook);
                }
                else if (finalI == 3)
                {
                    Intent adminchatsupport=new Intent(Emergency.this, AdminChatSupport.class);
                    startActivity(adminchatsupport);
                }

            });
        }
    }
    

    @Override
    public void onBackPressed() {

        if (EmergencydrawerLayout.isDrawerOpen((GravityCompat.START))) {

            EmergencydrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

}