package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

public class NewsFeed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout NewsfeeddrawerLayout;
    NavigationView NewsfeednavigationView;
    Toolbar Newsfeedtoolbar;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        authService = new AuthService();

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(NewsFeed.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });


        NewsfeeddrawerLayout = findViewById(R.id.NewsFeeddrawer_layout);
        NewsfeednavigationView = findViewById(R.id.Newsfeednav_view);
        Newsfeedtoolbar = findViewById(R.id.NewsFeedtool_bar);

        NewsfeednavigationView.bringToFront();
        setSupportActionBar(Newsfeedtoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, NewsfeeddrawerLayout, Newsfeedtoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        NewsfeeddrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NewsfeednavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (NewsfeeddrawerLayout.isDrawerOpen((GravityCompat.START))) {

            NewsfeeddrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.user_profile:
                break;
            case R.id.user_settings:
                break;
            case R.id.user_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(NewsFeed.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;
        }
        return true;
    }
}