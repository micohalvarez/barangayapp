package com.release.barangayapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.R;
import com.release.barangayapp.adapter.FragmentReportAdapter;
import com.release.barangayapp.adapter.FragmentResidentChatAdapter;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;

public class ResidentChatSupport extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ViewPager2 pager2;
    TabLayout tabLayout;

    DrawerLayout UserdrawerLayout;
    NavigationView UsernavigationView;
    Toolbar Usertoolbar;
    private AuthService authService;
    FragmentResidentChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_chat_support);

        authService = new AuthService();

        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(ResidentChatSupport.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });


        tabLayout = findViewById(R.id.residentchat_Tab);
        pager2= findViewById(R.id.residentchatviewpager);
        //ViewPager2
        FragmentManager fm= getSupportFragmentManager();
        adapter = new FragmentResidentChatAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Users"));

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



        UserdrawerLayout = findViewById(R.id.chatdrawer_layout);
        UsernavigationView = findViewById(R.id.chatnav_view);
        Usertoolbar = findViewById(R.id.chat_bar);


        UsernavigationView.bringToFront();
        setSupportActionBar(Usertoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, UserdrawerLayout, Usertoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        UserdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        UsernavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {

        if (UserdrawerLayout.isDrawerOpen((GravityCompat.START))) {

            UserdrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.user_home:
                Intent home = new Intent(ResidentChatSupport.this, UserMainMenu.class);
                startActivity(home);
                finish();
                break;
            case R.id.user_profile:
                Intent profile = new Intent(ResidentChatSupport.this, ResidentProfile.class);
                startActivity(profile);
                finish();
                break;
            case R.id.user_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(ResidentChatSupport.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        UserdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}