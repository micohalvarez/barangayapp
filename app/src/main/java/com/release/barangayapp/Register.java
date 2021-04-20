package com.release.barangayapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText AFullname, AUsername;
    EditText APassword, AConfPassword, APhone;
    Button ARegister;
    FirebaseAuth FAuth;
    ProgressBar ProgressB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_accountbarangay);

        AFullname = findViewById(R.id.RegPersonnelName);
        AUsername = findViewById(R.id.RegPersonnelEmail);
        APassword = findViewById(R.id.RegPersonnelPass);
        AConfPassword = findViewById(R.id.RegPersonnelCPass);
        APhone = findViewById(R.id.RegPersonnelNumber);

        ARegister = findViewById(R.id.RegRegisterPersonnelButton);
try {

    FAuth = FirebaseAuth.getInstance();
} catch (Exception E)
{System.out.println(E.toString());}
        ProgressB = findViewById(R.id.progressBar);

        ARegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BUsername = AUsername.getText().toString().trim();
                String BPassword = APassword.getText().toString().trim();

                if (TextUtils.isEmpty(BUsername))
                {
                   AUsername.setError("Username is Required");
                   return;
                }
                if (TextUtils.isEmpty(BPassword))
                {
                    APassword.setError("Password is Empty");
                    return;
                }
                if (BPassword.length() < 6)
                {
                    APassword.setError("Password must be more than 6 characters");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(BUsername).matches())
                {
                    AUsername.setError("Invalid Email");
                    return;
                }

                ProgressB.setVisibility(View.VISIBLE);
                FAuth.createUserWithEmailAndPassword(BUsername,BPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Register.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserLogin.class));
                            }
                            else
                            {
                                Toast.makeText(Register.this,"Error Occured" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() { }

}
