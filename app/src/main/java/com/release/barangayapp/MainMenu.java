package com.release.barangayapp;

import androidx.appcompat.app.AppCompatActivity;


import  android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


public class MainMenu extends AppCompatActivity {
    private Button Button_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button_user= findViewById(R.id.User_button);
        Button_user.setOnClickListener(v -> OpenUser());
    }

    public void OpenUser()
    {
        Intent intent=new Intent(this, UserLogin.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() { }
}