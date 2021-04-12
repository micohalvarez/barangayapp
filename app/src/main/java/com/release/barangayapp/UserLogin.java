package com.release.barangayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class UserLogin extends AppCompatActivity {

    private Button Login_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        Login_user= findViewById(R.id.User_btn_signin);
        Login_user.setOnClickListener(v -> LoginUser());
    }

    public void LoginUser()
    {
        Intent intent=new Intent(this, UserMainMenu.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() { }

}