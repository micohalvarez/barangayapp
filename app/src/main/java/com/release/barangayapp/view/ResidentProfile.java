package com.release.barangayapp.view;

import androidx.appcompat.app.AppCompatActivity;
import com.release.barangayapp.R;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResidentProfile extends AppCompatActivity {

    private AuthService authService;
    private UserObject curUser;
    private Button Button_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_profile);


        authService = new AuthService();

        authService.getUserDetails(value -> {
            curUser = value;
            TextView Name = findViewById(R.id.Resident_name);
            TextView mobile = findViewById(R.id.Resident_mobilenumber);
            TextView email = findViewById(R.id.Resident_email);
            TextView address = findViewById(R.id.Resident_address);


            Name.setText(curUser.getFullName());
            mobile.setText(curUser.getPhonenumber());
            email.setText(curUser.getEmail());
            address.setText(curUser.getAddress());


        });

        Button_ok = findViewById(R.id.Resident_button);

        Button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserMainMenu.class));
            }
        });

    }
}