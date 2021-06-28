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
    int hc = 0,a = 0,b = 0,c = 0;
    int condi=0, ch1=0, ch2=0, ch3=0, ch4=0, ch5=0, ch6=0, ch7=0, ch8=0, ch9=0, ch10=0, ch11=0, ch12=0;
    private String condition;


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

        String cs0 = "F";
        String cs1 = "DC";
        String cs2 = "ST";
        String cs3 = "SB";

        String os0 = "FT";
        String os1 = "A";
        String os2 = "RN";
        String os3 = "HA";
        String os4 = "D";

       String h = "Low Possibility";
       String h0 = "Possible";
       String h1 = "High Possibility";
       String h2 = "Warning";


        String c0 = "GOOD CONDITION";
        String c1 = "MILD CONDITION";
        String c2 = "SEVERE CONDITION";




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
                    ch1=1;
                }
                if (covidDryCough.isChecked())
                {
                    logBook.setSymptoms1(cs1);
                    ch2=1;
                }
                if (covidSoreThroat.isChecked())
                {
                    logBook.setSymptoms2(cs2);
                    ch3=1;
                }
                if (covidShortBreath.isChecked())
                {
                    logBook.setSymptoms3(cs3);
                    ch4=1;
                }
                if (covidFatigue.isChecked())
                {
                    logBook.setOtherSymptoms(os0);
                    ch5=1;
                }
                if (covidPain.isChecked())
                {
                    logBook.setOtherSymptoms1(os1);
                    ch6=1;
                }
                if (covidRunnyNose.isChecked())
                {
                    logBook.setOtherSymptoms2(os2);
                    ch7=1;
                }
                if (covidHeadAche.isChecked())
                {
                    logBook.setOtherSymptoms3(os3);
                    ch8=1;
                }
                if (covidDiarrhea.isChecked())
                {
                    logBook.setOtherSymptoms4(os4);
                    ch9=1;
                }
                if (healthCheck1.isChecked())
                {
                    a=1;
                    ch10=1;
                }
                if (healthCheck2.isChecked())
                {
                   b=1;
                    ch11=1;
                }
                if (healthCheck3.isChecked())
                {
                    c=1;
                    ch12=1;
                }
                hc = a+b+c;

                switch (hc)
                {
                    case 0:
                    {
                        condition = h;
                        break;
                    }
                    case 1:
                    {
                        condition = h0;
                        break;
                    }
                    case 2:
                    {
                        condition = h1;
                        break;
                    }
                    case 3:
                    {
                        condition = h2;
                        break;
                    }
                    default:
                    {break; }

                }

                condi= ch1+ch2+ch3+ch4+ch5+ch6+ch7+ch8+ch9+ch10+ch11+ch12;
                switch (condi)
                {
                    case 0:
                    {
                        logBook.setCondition(c0);
                        break;
                    }
                    case 1:
                    case 2: {
                        logBook.setCondition(c1);
                        break;
                    }
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    {
                        logBook.setCondition(c2);
                        break;
                    }
                    default:
                    {break; }
                }


                logBook.setHealthChecklist(condition);
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