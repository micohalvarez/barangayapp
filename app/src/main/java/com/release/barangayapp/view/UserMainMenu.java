package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridLayout;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

public class UserMainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout UserdrawerLayout;
    GridLayout UsermainGrid;
    NavigationView UsernavigationView;
    Toolbar Usertoolbar;
    private AuthService UserauthService;
    private FirebaseAuth UserLogoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);

        UserauthService = new AuthService();
        UserLogoutAuth = FirebaseAuth.getInstance();
        UserauthService.getUserDetails(value ->  {
            if(UserauthService.getAuthUser() == null) {
                Intent homeIntent = new Intent(UserMainMenu.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });


        UsermainGrid = findViewById(R.id.User_mainGrid);
        setSingleEvent(UsermainGrid);


        UserdrawerLayout = findViewById(R.id.Userdrawer_layout);
        UsernavigationView = findViewById(R.id.Usernav_view);
        Usertoolbar = findViewById(R.id.Usertool_bar);

        UsernavigationView.bringToFront();
        setSupportActionBar(Usertoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, UserdrawerLayout, Usertoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        UserdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        UsernavigationView.setNavigationItemSelectedListener(this);

        //For Signout in Firebase
        UserLogoutAuth.signOut();
    }


    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0; i<mainGrid.getChildCount();i++)
        {
            CardView cardview = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(v -> {

                if(finalI == 0)
                {
                    Intent newsfeed=new Intent(UserMainMenu.this, NewsFeed.class);
                    startActivity(newsfeed);

                }
                else if (finalI == 1)
                {
                    Intent emergency=new Intent(UserMainMenu.this, Emergency.class);
                    startActivity(emergency);

                }
                else if (finalI == 2)
                {
                    Intent covidsymptom=new Intent(UserMainMenu.this, CovidSymptomSurvey.class);
                    startActivity(covidsymptom);
                }
                else if (finalI == 3)
                {
                    Intent chatsupport=new Intent(UserMainMenu.this, ChatSupport.class);
                    startActivity(chatsupport);
                }

            });
        }
    }

    @Override
    public void onBackPressed() {

        if (UserdrawerLayout.isDrawerOpen((GravityCompat.START))) {

            UserdrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}