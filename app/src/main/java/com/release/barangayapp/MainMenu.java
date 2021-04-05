package com.release.barangayapp;

import androidx.appcompat.app.AppCompatActivity;


import  android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends AppCompatActivity {
    private Button Button_user, Button_barangay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button_user=(Button)findViewById(R.id.User_button);
        Button_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenUser();
            }
        });
    }

    public void OpenUser()
    {
        Intent intent=new Intent(this, UserLogin.class);
        startActivity(intent);
    }
}