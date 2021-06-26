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

public class UserMainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout UserdrawerLayout;
    GridLayout UsermainGrid;
    NavigationView UsernavigationView;
    Toolbar Usertoolbar;
    private AuthService authService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);

        authService = new AuthService();

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
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
                    Intent emergency=new Intent(UserMainMenu.this, EmergencyActivity.class);
                    startActivity(emergency);

                }
                else if (finalI == 2)
                {
                    Intent covidsymptom=new Intent(UserMainMenu.this, CovidSymptomSurveyActivity.class);
                    startActivity(covidsymptom);
                }
                else if (finalI == 3)
                {
                    Intent chatsupport=new Intent(UserMainMenu.this, ResidentChatSupport.class);
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

        switch (item.getItemId()){
            case R.id.user_home:
                Intent home = new Intent(UserMainMenu.this, UserMainMenu.class);
                startActivity(home);
                finish();
                break;
            case R.id.user_profile:
                Intent profile = new Intent(UserMainMenu.this, ResidentProfile.class);
                startActivity(profile);
                finish();
                break;
            case R.id.user_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(UserMainMenu.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
               authService.signOut();
                break;

        }
        UserdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
