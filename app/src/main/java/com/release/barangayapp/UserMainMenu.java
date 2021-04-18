package com.release.barangayapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserMainMenu extends AppCompatActivity{


    DrawerLayout drawerLayout;
    GridLayout mainGrid;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        mainGrid = findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);


        /*drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.tool_bar);

        navigationView.bringToFront();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_view);*/


    }


    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0; i<mainGrid.getChildCount();i++)
        {
            CardView cardview = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(v -> {

                if(finalI == 0)
                {

                }
                else if (finalI == 1)
                {
                    Intent intent=new Intent(UserMainMenu.this, Emergency.class);
                    startActivity(intent);

                }
                else if (finalI == 2)
                {

                }
                else if (finalI == 3)
                {

                }

            });
        }
    }

    @Override
    public void onBackPressed() { }

    /*@Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }*/
}