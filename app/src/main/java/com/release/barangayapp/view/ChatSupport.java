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
import android.widget.GridLayout;

import com.google.android.material.navigation.NavigationView;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

public class ChatSupport extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout UserdrawerLayout;
    NavigationView UsernavigationView;
    Toolbar Usertoolbar;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_support);


        authService = new AuthService();

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(ChatSupport.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });



        UserdrawerLayout = findViewById(R.id.chatdrawer_layout);
        UsernavigationView = findViewById(R.id.chatnav_view);
        Usertoolbar = findViewById(R.id.chat_bar);

        UsernavigationView.bringToFront();
        setSupportActionBar(Usertoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, UserdrawerLayout, Usertoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        UserdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        UsernavigationView.setNavigationItemSelectedListener(this);

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
                Intent home = new Intent(ChatSupport.this, UserMainMenu.class);
                startActivity(home);
                finish();
                break;
            /*case R.id.user_profile:
                break;*/
            case R.id.user_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(ChatSupport.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        UserdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}