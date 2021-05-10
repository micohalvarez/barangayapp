package com.release.barangayapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;


import com.release.barangayapp.model.LogBook;
import com.release.barangayapp.R;
import com.release.barangayapp.service.LogBookService;

public class CovidSymptomSurvey extends AppCompatActivity {

    CheckBox covidHeadAche, covidFatigue, covidRunnyNose;
    CheckBox covidShortBreath, covidSoreThroat, covidDryCough;
    CheckBox covidFever, covidDiarrhea, covidPain;
    Button covidSubmit;
    boolean headache, fatigue, runnynose, shortbreath, sorethroat;
    boolean drycough, fever, diarrhea, pain;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        covidSubmit = findViewById(R.id.covidSurveySubmit);

        
        covidHeadAche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidHeadAche.isChecked())
                {
                    headache = true;
                }
                else
                {
                    headache = false;
                }
            }
        });
        covidFatigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidFatigue.isChecked())
                {
                    fatigue = true;
                }
                else
                {
                    fatigue = false;
                }
            }
        });
        covidRunnyNose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidRunnyNose.isChecked())
                {
                    runnynose = true;
                }
                else
                {
                    runnynose = false;
                }
            }
        });
        covidShortBreath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidShortBreath.isChecked())
                {
                    shortbreath = true;
                }
                else
                {
                    shortbreath = false;
                }
            }
        });
        covidSoreThroat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidSoreThroat.isChecked())
                {
                    sorethroat = true;
                }
                else
                {
                    sorethroat = false;
                }
            }
        });
        covidDryCough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidDryCough.isChecked())
                {
                    drycough = true;
                }
                else
                {
                    drycough = false;
                }
            }
        });
        covidFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidFever.isChecked())
                {
                    fever = true;
                }
                else
                {
                    fever = false;
                }
            }
        });
        covidDiarrhea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidDiarrhea.isChecked())
                {
                    diarrhea = true;
                }
                else
                {
                    diarrhea = false;
                }
            }
        });
        covidPain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (covidPain.isChecked())
                {
                    pain = true;
                }
                else
                {
                    pain = false;
                }
            }
        });
             //On Clicking Submit
        covidSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headache == true)
                {
                    System.out.println("true");
                }
                else if (headache == false)
                {
                    System.out.println("false");
                }

            }
        });

    }


}