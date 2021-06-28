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

public class CovidSymptomSurveyActivity extends AppCompatActivity {

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
                Intent homeIntent = new Intent(CovidSymptomSurveyActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
            else
                userId = authService.getAuthUser().getUid();


        });

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(CovidSymptomSurveyActivity.this, MainMenu.class);
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

        String cs0 = "0";
        String cs1 = "1";
        String cs2 = "2";
        String cs3 = "3";

        String os0 = "0";
        String os1 = "1";
        String os2 = "2";
        String os3 = "3";
        String os4 = "4";

        String hc0 = "0";
        String hc1 = "1";
        String hc2 = "2";

             //On Clicking Submit
        covidSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*CheckBox covidHeadAche.isChecked(), covidFatigue.isChecked(), covidRunnyNose.isChecked();
                CheckBox covidShortBreath.isChecked(), covidSoreThroat.isChecked(), covidDryCough.isChecked();
                CheckBox covidFever.isChecked(), covidDiarrhea.isChecked(), covidPain.isChecked();
                CheckBox healthCheck1.isChecked(), healthCheck2.isChecked(), healthCheck3.isChecked();*/

                if (covidFever.isChecked())
                {
                    logBook.setSymptoms(cs0);
                }
                if (covidDryCough.isChecked())
                {
                    logBook.setSymptoms1(cs1);
                }
                if (covidSoreThroat.isChecked())
                {
                    logBook.setSymptoms2(cs2);
                }
                if (covidShortBreath.isChecked())
                {
                    logBook.setSymptoms3(cs3);
                }
                if (covidFatigue.isChecked())
                {
                    logBook.setOtherSymptoms(os0);
                }
                if (covidPain.isChecked())
                {
                    logBook.setOtherSymptoms1(os1);
                }
                if (covidRunnyNose.isChecked())
                {
                    logBook.setOtherSymptoms2(os2);
                }
                if (covidHeadAche.isChecked())
                {
                    logBook.setOtherSymptoms3(os3);
                }
                if (covidDiarrhea.isChecked())
                {
                    logBook.setOtherSymptoms4(os4);
                }
                if (healthCheck1.isChecked())
                {
                    logBook.setHealthChecklist(hc0);
                }
                if (healthCheck2.isChecked())
                {
                    logBook.setHealthChecklist1(hc1);
                }
                if (healthCheck3.isChecked())
                {
                    logBook.setHealthChecklist2(hc2);
                }



                logBook.setFullName(curUser.getFullName());
                logBook.setPhonenumber(curUser.getPhonenumber());
                logBook.setAddress(curUser.getAddress());
                logBook.setEmail(curUser.getEmail());
                logBook.setSurveyDate(surveydate);
                logBook.setUserId(userId);
                logBookService.saveData(logBook);
                Toast.makeText(CovidSymptomSurveyActivity.this, "Survey Data is submitted successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), CovidResidentProfileActivity.class));
            }
        });

    }


}