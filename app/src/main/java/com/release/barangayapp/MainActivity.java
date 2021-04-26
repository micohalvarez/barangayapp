    package com.release.barangayapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.MyCallBack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    private AuthService authService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        authService = new AuthService();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authService.getUserDetails(value -> new Handler().postDelayed(() -> {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
            else{
                if(value.getRole() == 2) {
                    Intent homeIntent = new Intent(MainActivity.this, UserMainMenu.class);
                    startActivity(homeIntent);
                    finish();
                }
                else {
                    Intent homeIntent = new Intent(MainActivity.this, AdminMainMenu.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT));


    }
}