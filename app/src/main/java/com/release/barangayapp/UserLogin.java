package com.release.barangayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class UserLogin extends AppCompatActivity {

    private Button Login_user, Login_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        Login_admin=findViewById(R.id.User_cntlgn);
        Login_admin.setOnClickListener(v -> LoginAdmin());
        Login_user= findViewById(R.id.User_btn_signin);
        Login_user.setOnClickListener(v -> LoginUser());
    }

    public void LoginUser()
    {
        Intent usermainmenu=new Intent(this, UserMainMenu.class);
        startActivity(usermainmenu);
    }


    public void LoginAdmin()
    {
        Intent adminmainmenu=new Intent(this, AdminMainMenu.class);
        startActivity(adminmainmenu);
    }
    @Override
    public void onBackPressed() { }

}