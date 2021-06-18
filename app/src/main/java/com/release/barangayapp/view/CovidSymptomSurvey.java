package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.release.barangayapp.R;
import com.release.barangayapp.model.LogBook;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.LogBookService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CovidSymptomSurvey extends AppCompatActivity {

    CheckBox covidHeadAche, covidFatigue, covidRunnyNose;
    CheckBox covidShortBreath, covidSoreThroat, covidDryCough;
    CheckBox covidFever, covidDiarrhea, covidPain;
    CheckBox healthCheck1, healthCheck2, healthCheck3;
    Button covidSubmit;
    LogBookService logBookService;
    LogBook logBook;
    TextView surveyDateView;

    private AuthService authService;
    private String userId;
    private String surveydate;
    private UserObject curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        authService = new AuthService();
        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(CovidSymptomSurvey.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
            else
                userId = authService.getAuthUser().getUid();


        });

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(CovidSymptomSurvey.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
            else{
                if(value != null)
                    curUser = value;
            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_symptom_survey);

        covidHeadAche = findViewById(R.id.covidHeadAcheButton);
        covidFatigue = findViewById(R.id.covidFatigueButton);
        covidRunnyNose = findViewById(R.id.covidRunnyNoseButton);
        covidShortBreath = findViewById(R.id.covidShortBreathButton);
        covidSoreThroat = findViewById(R.id.covidSoreThroatButton);
        covidDryCough = findViewById(R.id.covidDryCoughButton);
        covidFever = findViewById(R.id.covidFeverButton);
        covidDiarrhea = findViewById(R.id.covidDiarrheaButton);
        covidPain = findViewById(R.id.covidPainButton);
        surveyDateView = findViewById(R.id.survey_dateview);
        healthCheck1 = findViewById(R.id.covidCheckList1);
        healthCheck2 = findViewById(R.id.covidCheckList2);
        healthCheck3 = findViewById(R.id.covidCheckList3);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
        surveyDateView = findViewById(R.id.survey_dateview);
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        surveyDateView.setText(currentdate);
        surveydate = surveyDateView.getText().toString().trim();

        covidSubmit = findViewById(R.id.covidSurveySubmit);

        logBookService = new LogBookService();
        logBook = new LogBook();

        
        covidHeadAche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidHeadAche.isChecked())
                {
                    logBook.getOtherSymptoms().add(3);
                }
                else
                {
                    logBook.getOtherSymptoms().remove(Integer.valueOf(3));
                }
            }
        });
        covidFatigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidFatigue.isChecked())
                {
                    logBook.getOtherSymptoms().add(0);
                }
                else
                {
                    logBook.getOtherSymptoms().remove(Integer.valueOf(0));
                }
            }
        });
        covidRunnyNose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidRunnyNose.isChecked())
                {
                    logBook.getOtherSymptoms().add(2);
                }
                else
                {
                    logBook.getOtherSymptoms().remove(Integer.valueOf(2));
                }
            }
        });
        covidShortBreath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidShortBreath.isChecked())
                {
                    logBook.getSymptoms().add(3);
                }
                else
                {
                    logBook.getSymptoms().remove(Integer.valueOf(3));
                }
            }
        });
        covidSoreThroat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidSoreThroat.isChecked())
                {
                    logBook.getSymptoms().add(2);
                }
                else
                {
                    logBook.getSymptoms().remove(Integer.valueOf(2));
                }
            }
        });
        covidDryCough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidDryCough.isChecked())
                {
                    logBook.getSymptoms().add(1);
                }
                else
                {
                    logBook.getSymptoms().remove(Integer.valueOf(1));
                }
            }
        });
        covidFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidFever.isChecked())
                {
                    logBook.getSymptoms().add(0);
                }
                else
                {
                    logBook.getSymptoms().remove(Integer.valueOf(0));
                }
            }
        });
        covidDiarrhea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidDiarrhea.isChecked())
                {
                    logBook.getOtherSymptoms().add(4);
                }
                else
                {
                    logBook.getOtherSymptoms().remove(Integer.valueOf(4));
                }
            }
        });
        covidPain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidPain.isChecked())
                {
                    logBook.getOtherSymptoms().add(1);
                }
                else
                {
                    logBook.getOtherSymptoms().remove(Integer.valueOf(1));
                }
            }
        });
        healthCheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (healthCheck1.isChecked())
                {
                    logBook.getHealthChecklist().add(0);
                }
                else
                {
                    logBook.getHealthChecklist().remove(Integer.valueOf(0));
                }
            }
        });
        healthCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (healthCheck2.isChecked())
                {
                    logBook.getHealthChecklist().add(1);
                }
                else
                {
                    logBook.getHealthChecklist().remove(Integer.valueOf(1));
                }
            }
        });
        healthCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (healthCheck3.isChecked())
                {
                    logBook.getHealthChecklist().add(2);
                }
                else
                {
                    logBook.getHealthChecklist().remove(Integer.valueOf(2));
                }
            }
        });
             //On Clicking Submit
        covidSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logBook.setFullName(curUser.getFullName());
                logBook.setPhonenumber(curUser.getPhonenumber());
                logBook.setAddress(curUser.getAddress());
                logBook.setEmail(curUser.getEmail());
                logBook.setSurveyDate(surveydate);
                logBook.setUserId(userId);
                logBookService.saveData(logBook);
                Toast.makeText(CovidSymptomSurvey.this, "Survey Data is submitted successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), CovidResidentProfile.class));
            }
        });

    }


}