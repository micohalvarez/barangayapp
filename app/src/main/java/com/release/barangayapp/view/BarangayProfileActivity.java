package com.release.barangayapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.release.barangayapp.R;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;

public class BarangayProfileActivity extends AppCompatActivity {

    private AuthService authService;
    private UserObject curUser;
    private Button Button_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_profile);

        authService = new AuthService();

        authService.getUserDetails(value -> {
            curUser = value;
            TextView Name = findViewById(R.id.Barangay_name);
            TextView mobile = findViewById(R.id.Barangay_mobilenumber);
            TextView email = findViewById(R.id.Barangay_email);
            TextView address = findViewById(R.id.Barangay_address);


            Name.setText(curUser.getFullName());
            mobile.setText(curUser.getPhonenumber());
            email.setText(curUser.getEmail());
            address.setText(curUser.getAddress());


        });

        Button_ok = findViewById(R.id.Barangay_button);

        Button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminMainMenuActivity.class));
            }
        });
    }
}