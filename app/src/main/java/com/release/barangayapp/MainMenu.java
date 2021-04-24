package com.release.barangayapp;

import androidx.appcompat.app.AppCompatActivity;


import  android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


public class MainMenu extends AppCompatActivity {
    private Button Button_user, Button_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button_user= findViewById(R.id.User_button);
        Button_user.setOnClickListener(v -> OpenUser());
        Button_admin= findViewById(R.id.AboutUs_button);
        Button_admin.setOnClickListener(v -> OpenAdmin());
    }

    public void OpenUser()
    {

        Intent userlogin=new Intent(this, UserLogin.class);
        startActivity(userlogin);
    }

    public void OpenAdmin()
    {
        Intent aboutus=new Intent(this, AboutUs.class);
        startActivity(aboutus);
    }



    @Override
    public void onBackPressed() { }
}