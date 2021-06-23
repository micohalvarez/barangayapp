package com.release.barangayapp.view;

import androidx.appcompat.app.AppCompatActivity;


import  android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.release.barangayapp.R;


public class MainMenu extends AppCompatActivity {
    private Button Button_user, Button_admin;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        textView= findViewById(R.id.terms);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Terms_and_condition.class));
            }
        });

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