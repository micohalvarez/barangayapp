package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.release.barangayapp.adapter.PagerAdapter;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;



public class NotificationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout NotifdrawerLayout;
    FloatingActionButton Addannouncement;
    NavigationView NotifnavigationView;
    Toolbar Notiftoolbar;
    private AuthService authService;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem notif, announcement;
    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        authService = new AuthService();
        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(NotificationsActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });

        pager = findViewById(R.id.viewpager);
        mTabLayout= findViewById(R.id.Notif_Announcement_Tab);
        notif= findViewById(R.id.Notif_Tab);
        announcement= findViewById(R.id.Announcement_Tab);

        NotifdrawerLayout = findViewById(R.id.Notificationsdrawer_layout);
        NotifnavigationView = findViewById(R.id.Notificationsnav_view);
        Notiftoolbar = findViewById(R.id.Notificationstool_bar);

        NotifnavigationView.bringToFront();
        setSupportActionBar(Notiftoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, NotifdrawerLayout, Notiftoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        NotifdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NotifnavigationView.setNavigationItemSelectedListener(this);

        Addannouncement = findViewById(R.id.AddAnnouncement);

        Addannouncement.setOnClickListener(v -> {
            //Go to CreateAnnouncementActivity
            Intent emergency=new Intent(NotificationsActivity.this, CreateAnnouncementActivity.class);
            startActivity(emergency);
        });

        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount());
        pager.setAdapter(adapter);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("heehe");

                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.admin_profile:
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(NotificationsActivity.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_settings:
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(NotificationsActivity.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        NotifdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    
}