package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.release.barangayapp.R;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.NotificationService;
import com.release.barangayapp.service.UserService;

public class Register extends AppCompatActivity {

    EditText AFullname, AUsername, AAddress;
    EditText APassword, AConfPassword, APhone;
    Switch ADesignator;
    TextView ADesignatorDetail;
    Button ARegister;
    FirebaseAuth FAuth;
    ProgressBar ProgressB;
    int Role = 1;
    private UserService userService;
    private NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        AFullname = findViewById(R.id.RegPersonnelName);
        AUsername = findViewById(R.id.RegPersonnelEmail);
        AAddress = findViewById(R.id.RegPersonnelAddress);
        APassword = findViewById(R.id.RegPersonnelPass);
        AConfPassword = findViewById(R.id.RegPersonnelCPass);
        APhone = findViewById(R.id.RegPersonnelNumber);


        FAuth = FirebaseAuth.getInstance();
        ARegister = findViewById(R.id.RegRegisterPersonnelButton);
        ADesignator = findViewById(R.id.SwitchAccountDesignator);
        ADesignatorDetail = findViewById(R.id.AccountDesignateDetail);
        ProgressB = findViewById(R.id.progressBar);

        notificationService = new NotificationService();

        ADesignator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ADesignator.isChecked())
                {ADesignatorDetail.setText("CITIZEN USER");
                Role = 2;}
                else
                {ADesignatorDetail.setText("POLICE OFFICIAL USER");
                Role = 1;}
            }
        });


        ARegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String compPass = APassword.getText().toString().trim();
                String compCPass = AConfPassword.getText().toString().trim();
                String BUsername = AUsername.getText().toString().trim();
                String BPassword = AConfPassword.getText().toString().trim();
                String BFullname = AFullname.getText().toString().trim();
                String BAddress = AAddress.getText().toString().trim();
                String BPhone = APhone.getText().toString().trim();

                if (!compPass.equals(compCPass))
                {
                    AConfPassword.setError("Confirmed Password is not the same");
                    return;
                }
                else if (TextUtils.isEmpty(BFullname))
                {
                    AFullname.setError("Full Name is Required");
                    return;
                }
                else if (TextUtils.isEmpty(BPhone))
                {
                    APhone.setError("Phone Number Field is required");
                    return;
                }
                else if (TextUtils.isEmpty(BAddress))
                {
                    AAddress.setError("Address Field is Required");
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
                                FirebaseUser user = FAuth.getCurrentUser();
                                register(user.getUid());
                                Toast.makeText(Register.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AdminMainMenuActivity.class));
                            }
                            else
                            {
                                Toast.makeText(Register.this,"Error Occurred" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                ProgressB.setVisibility(View.INVISIBLE);
                            }
                    }
                });
             }
            }
        });
    }

    public void register(String userId){

        userService = new UserService();
        UserObject user = new UserObject();
        //Set Objects
        user.setRole(Role);
        user.setEmail(AUsername.getText().toString().trim());
        user.setFullName(AFullname.getText().toString().trim());
        user.setAddress(AAddress.getText().toString().trim());
        user.setPhonenumber(APhone.getText().toString().trim());

        userService.saveData(user,userId);

        if(Role == 1){
            notificationService.getToken(resp ->{
                if(resp){
                    notificationService.pushToken(value->{
                        if(value){
                            Toast.makeText(Register.this,"Successfully registered admin token Occurred" ,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), AdminMainMenuActivity.class));
    }
}
