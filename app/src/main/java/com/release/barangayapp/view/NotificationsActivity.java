package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.release.barangayapp.adapter.FragmentNotificationAdapter;
import com.release.barangayapp.R;
import com.release.barangayapp.service.AuthService;

public class NotificationsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout NotifdrawerLayout;
    FloatingActionButton Addannouncement;
    NavigationView NotifnavigationView;
    Toolbar Notiftoolbar;
    private AuthService authService;
    ViewPager2 pager2;
    TabLayout tabLayout;
    FragmentNotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        authService = new AuthService();
        authService.getUserDetails(value -> {
            if (authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(NotificationsActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });

        pager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.Notif_Announcement_Tab);

        NotifdrawerLayout = findViewById(R.id.Notificationsdrawer_layout);
        NotifnavigationView = findViewById(R.id.Adminnav_view);
        Notiftoolbar = findViewById(R.id.Notificationstool_bar);

        NotifnavigationView.bringToFront();
        setSupportActionBar(Notiftoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, NotifdrawerLayout, Notiftoolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NotifdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NotifnavigationView.setNavigationItemSelectedListener(this);

        Addannouncement = findViewById(R.id.AddAnnouncement);

        Addannouncement.setOnClickListener(v -> {
            // Go to CreateAnnouncementActivity
            Intent emergency = new Intent(NotificationsActivity.this, CreateAnnouncementActivity.class);
            startActivity(emergency);
        });

        // ViewPager2
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentNotificationAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Notification"));
        tabLayout.addTab(tabLayout.newTab().setText("Announcement"));

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.admin_home:
                Intent home = new Intent(NotificationsActivity.this, AdminMainMenu.class);
                startActivity(home);
                finish();
                break;
            case R.id.admin_profile:
                Intent profile = new Intent(NotificationsActivity.this, BarangayProfile.class);
                startActivity(profile);
                finish();
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(NotificationsActivity.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_logout:
                // For Signout in Firebase
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