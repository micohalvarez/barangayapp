package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.NotificationService;

public class UserLogin extends AppCompatActivity {


        Button Login_user;
        EditText Username,Password;
        FirebaseAuth FirebaseLoginAuth;
        ProgressBar PBar;
        private NotificationService notificationService;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_login);



            Username = findViewById(R.id.User_EmailUsername);
            Password = findViewById(R.id.User_Password);

            Login_user = findViewById(R.id.User_btn_signin);
            FirebaseLoginAuth = FirebaseAuth.getInstance();
            PBar = findViewById(R.id.progressBar1);
            notificationService = new NotificationService();


                Login_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String StgUsername = Username.getText().toString().trim();
                        String StgPassword = Password.getText().toString().trim();
                        if (TextUtils.isEmpty(StgUsername)) {
                            Username.setError("Username is Required");
                            return;
                        }
                        if (TextUtils.isEmpty(StgPassword)) {
                            Password.setError("Password is Empty");
                            return;
                        }
                        if (StgPassword.length() < 6) {
                            Password.setError("Password must be more than 6 characters");
                            return;
                        }
                        if (!Patterns.EMAIL_ADDRESS.matcher(StgUsername).matches()) {
                            Username.setError("Invalid Email");
                            return;
                        }
                        PBar.setVisibility(View.VISIBLE);
                        FirebaseLoginAuth.signInWithEmailAndPassword(StgUsername, StgPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    AuthService authService = new AuthService();

                                    authService.getUserDetails(value -> {
                                            if (value == null) {
                                                Toast.makeText(UserLogin.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                                                finish();
                                            } else if (value.getRole() == 2) {
                                                Toast.makeText(UserLogin.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), UserMainMenu.class));
                                                finish();
                                            } else {
                                                notificationService.getToken(resp ->{
                                                    if(resp){
                                                        notificationService.pushToken(response->{
                                                            if(response){
                                                                Toast.makeText(UserLogin.this,"Successfully registered admin token Occurred" ,Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                                Toast.makeText(UserLogin.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), AdminMainMenu.class));
                                                finish();
                                            }
                                    });

                                } else {
                                    Toast.makeText(UserLogin.this, "Error Occured: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    PBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                });


            }

        }


