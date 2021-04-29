    package com.release.barangayapp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;

import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

    public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    private AuthService authService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        authService = new AuthService();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new Handler().postDelayed(() ->{
//            Intent homeIntent =new Intent(MainActivity.this, MainMenu.class);
//            startActivity(homeIntent);
//            finish();
//        },SPLASH_TIME_OUT);
        authService.getUserDetails(value -> new Handler().postDelayed(() -> {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
            else{
                if ( value == null) {
                    Intent homeIntent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(homeIntent);
                    finish();
                }
                else if(value.getRole() == 2) {
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