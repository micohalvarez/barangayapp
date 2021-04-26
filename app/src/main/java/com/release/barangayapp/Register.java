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
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.model.LogBook;
import com.release.barangayapp.service.AnnouncementService;
import com.release.barangayapp.service.LogBookService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    EditText AFullname, AUsername;
    EditText APassword, AConfPassword, APhone;
    Button ARegister;
    FirebaseAuth FAuth;
    ProgressBar ProgressB;
    private AnnouncementService announcementService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //for saving
//        DateFormat Date = DateFormat.getDateInstance();
//        Calendar cals = Calendar.getInstance();
//
//        Announcement lb = new Announcement();
//
//        String currentDate = Date.format(cals.getTime());
//        lb.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
//        lb.setTitle("Test Announcement");
//        lb.setCreatedAt(currentDate);
//
//        announcementService = new AnnouncementService();
//        announcementService.saveData(lb);

        //for retrieving
//       ArrayList<Announcement> announcements =  announcementService.getData();
//
//       for(Announcement announcement : announcements){
//
//       }



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
                String compPass = APassword.getText().toString().trim();
                String compCPass = AConfPassword.getText().toString().trim();
                String BUsername = AUsername.getText().toString().trim();
                String BPassword = AConfPassword.getText().toString().trim();
System.out.println(compPass);
System.out.println(compCPass);
                if (!compPass.equals(compCPass))
                {
                    AConfPassword.setError("Confirmed Password is not the same");
                    return;
                }

                else if (TextUtils.isEmpty(BUsername))
                {
                   AUsername.setError("Username is Required");
                   return;
                }
                else if (TextUtils.isEmpty(BPassword))
                {
                    AConfPassword.setError("Password is Empty");
                    return;
                }
                else if (BPassword.length() < 6)
                {
                    AConfPassword.setError("Password must be more than 6 characters");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(BUsername).matches())
                {
                    AUsername.setError("Invalid Email");
                    return;
                }
                else
                    {
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

            }
        });
    }
    @Override
    public void onBackPressed() { }

}
