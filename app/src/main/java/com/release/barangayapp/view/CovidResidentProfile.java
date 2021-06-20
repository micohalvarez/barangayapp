package com.release.barangayapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.release.barangayapp.R;
import com.release.barangayapp.adapter.FragmentCovidUserProfileAdapter;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CovidResidentProfile extends AppCompatActivity {

    ViewPager2 pager2;
    TabLayout tabLayout;
    FragmentCovidUserProfileAdapter adapter;
    FloatingActionButton back;
    Button button_update;
    private AuthService authService;
    private UserObject curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_resident_profile);


        authService = new AuthService();

        authService.getUserDetails(value -> {
            curUser = value;
            TextView Name = findViewById(R.id.CovidUser_name);


            Name.setText(curUser.getFullName());


        });



        button_update= findViewById(R.id.Covid_updatebutton);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CovidSymptomSurvey.class));
            }
        });



        back = findViewById(R.id.Back);
        back.setOnClickListener(v -> {
            //Go to CreateAnnouncementActivity
            Intent back=new Intent(CovidResidentProfile.this, UserMainMenu.class);
            startActivity(back);
        });

        pager2 = findViewById(R.id.reportviewpager);
        tabLayout= findViewById(R.id.Report_Tab);


        //ViewPager2
        FragmentManager fm= getSupportFragmentManager();
        adapter = new FragmentCovidUserProfileAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Summary"));
        tabLayout.addTab(tabLayout.newTab().setText("Updates"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }
}