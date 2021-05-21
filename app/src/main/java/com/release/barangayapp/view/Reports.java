package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.release.barangayapp.R;
import com.release.barangayapp.adapter.FragmentNotificationAdapter;
import com.release.barangayapp.adapter.FragmentReportAdapter;
import com.release.barangayapp.service.AuthService;

public class Reports extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    DrawerLayout AdmindrawerLayout;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    private AuthService authService;
    ViewPager2 pager2;
    TabLayout tabLayout;
    FragmentReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        authService = new AuthService();
        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(Reports.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });

        pager2 = findViewById(R.id.reportviewpager);
        tabLayout= findViewById(R.id.Report_Tab);




        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.Adminnav_view);
        Admintoolbar = findViewById(R.id.Admintool_bar);

        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, AdmindrawerLayout, Admintoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AdmindrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        AdminnavigationView.setNavigationItemSelectedListener(this);


        //ViewPager2
        FragmentManager fm= getSupportFragmentManager();
        adapter = new FragmentReportAdapter(fm, getLifecycle());
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

    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0; i<mainGrid.getChildCount();i++)
        {
            CardView cardview = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(v -> {

                if(finalI == 0)
                {
                    Intent notification=new Intent(Reports.this, NotificationsActivity.class);
                    startActivity(notification);

                }
                else if (finalI == 1)
                {
                    Intent reports=new Intent(Reports.this, Reports.class);
                    startActivity(reports);

                }
                else if (finalI == 2)
                {
                    Intent loogbook=new Intent(Reports.this, Logbook.class);
                    startActivity(loogbook);
                }
                else if (finalI == 3)
                {
                    Intent adminchatsupport=new Intent(Reports.this, AdminChatSupport.class);
                    startActivity(adminchatsupport);
                }

            });
        }
    }

    @Override
    public void onBackPressed() {

        if (AdmindrawerLayout.isDrawerOpen((GravityCompat.START))) {

            AdmindrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.admin_profile:
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(Reports.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_settings:
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(Reports.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        AdmindrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}