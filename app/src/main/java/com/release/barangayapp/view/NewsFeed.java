package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.release.barangayapp.R;
import com.release.barangayapp.adapter.AnnouncementRecyclerViewAdapter;
import com.release.barangayapp.adapter.FragmentNewsFeedAdapter;
import com.release.barangayapp.adapter.FragmentNotificationAdapter;
import androidx.fragment.app.FragmentManager;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.service.AnnouncementService;
import com.release.barangayapp.service.AuthService;

import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout NotifdrawerLayout;
    NavigationView NotifnavigationView;
    Toolbar Notiftoolbar;
    private AuthService authService;


    ViewPager2 pager2;
    TabLayout tabLayout;
    FragmentNewsFeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        authService = new AuthService();

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(NewsFeed.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });


        pager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.Notif_Announcement_Tab);

        NotifdrawerLayout = findViewById(R.id.NewsFeeddrawer_layout);
        NotifnavigationView = findViewById(R.id.Newsfeednav_view);
        Notiftoolbar = findViewById(R.id.Notificationstool_bar);

        NotifnavigationView.bringToFront();
        setSupportActionBar(Notiftoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, NotifdrawerLayout, Notiftoolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NotifdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NotifnavigationView.setNavigationItemSelectedListener(this);


        // ViewPager2
        pager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.Notif_Announcement_Tab);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentNewsFeedAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Announcements"));

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

    @Override
    public void onBackPressed() {
        if (NotifdrawerLayout.isDrawerOpen((GravityCompat.START))) {

            NotifdrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.user_home:
                Intent home = new Intent(NewsFeed.this, UserMainMenu.class);
                startActivity(home);
                finish();
                break;
            /*case R.id.user_profile:
                break;*/
            case R.id.user_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(NewsFeed.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;
        }
        NotifdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}