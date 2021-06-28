package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.release.barangayapp.R;
import com.release.barangayapp.adapter.LogBookRecyclerViewAdapter;
import com.release.barangayapp.model.LogBook;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.LogBookService;

import java.util.ArrayList;

public class LogbookActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,LogBookRecyclerViewAdapter.OnLogBookListener{

    DrawerLayout AdmindrawerLayout;
    NavigationView AdminnavigationView;
    Toolbar Admintoolbar;
    private AuthService authService;
    private Button Button_summary;

    private RecyclerView recyclerView;
    private LogBookRecyclerViewAdapter Adapter;
    private ArrayList<LogBook> logbookholder;
    private LogBookService logBookService;
    LogBook logBook;

    private DatabaseReference logbookRef;
    private FirebaseDatabase firebaseDatabase;
    private String fever,dryCough,soreThroat,shortBreath,fatigue,aches,runnyNose,headAche,diarrhea,h0,h1,h2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logbook);

        authService = new AuthService();
        authService.getUserDetails(value ->  {
            if(authService.getAuthUser() == null) {
                Intent homeIntent = new Intent(LogbookActivity.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        });


        recyclerView = findViewById(R.id.logbook_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        logbookholder = new ArrayList<>();
        logBookService = new LogBookService();
        logBook = new LogBook();

        initAdapter();

        AdmindrawerLayout = findViewById(R.id.Admindrawer_layout);
        AdminnavigationView = findViewById(R.id.Loogbook_view);
        Admintoolbar = findViewById(R.id.Loogbook_bar);

        AdminnavigationView.bringToFront();
        setSupportActionBar(Admintoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, AdmindrawerLayout, Admintoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AdmindrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        AdminnavigationView.setNavigationItemSelectedListener(this);





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
            case R.id.admin_home:
                Intent home = new Intent(LogbookActivity.this, AdminMainMenuActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.admin_profile:
                Intent profile = new Intent(LogbookActivity.this, BarangayProfileActivity.class);
                startActivity(profile);
                finish();
                break;
            case R.id.admin_register:
                Intent registerIntent = new Intent(LogbookActivity.this, Register.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.admin_logout:
                //For Signout in Firebase
                Intent LogoutIntent = new Intent(LogbookActivity.this, MainMenu.class);
                startActivity(LogoutIntent);
                finish();
                authService.signOut();
                break;

        }
        AdmindrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isLoading = false;
    private int nextLimit = 11;

    public void initAdapter(){

        logBookService.getData(value -> {
            logbookholder = value;
            System.out.println(logbookholder.size());
            initializeAdapter();
            initScrollListener();
        },10);

    }

    private void initializeAdapter() {

        Adapter = new LogBookRecyclerViewAdapter(logbookholder,this );
        recyclerView.setAdapter(Adapter);
    }
    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == logbookholder.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        recyclerView.post(() -> {
            logbookholder.add(null);
            Adapter.notifyDataSetChanged();
        });

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            logbookholder.remove(logbookholder.size() - 1);
            int scrollPosition = logbookholder.size();
            Adapter.notifyItemRemoved(scrollPosition);


            logBookService.loadMoreData(value -> {
                logbookholder = value;
                Adapter.notifyDataSetChanged();
                isLoading = false;
            },nextLimit ,logbookholder);

        }, 2000);
    }


    @Override
    public void onLogBookClick(int position) {
        AlertDialog.Builder imageDialog = new AlertDialog.Builder(LogbookActivity.this);
        LayoutInflater inflater = (LayoutInflater) LogbookActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final AlertDialog dialog = imageDialog.create();

        View layout = inflater.inflate(R.layout.logbook_details,
               LogbookActivity.this.findViewById(R.id.layout_logroot));

        Button mStart = layout.findViewById(R.id.button_close);


        mStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        setLogBookDetails(layout,position);
        dialog.setView(layout); 
        dialog.show();
    }

    private void setLogBookDetails(View mView,int position){
        TextView date = mView.findViewById(R.id.date);
        TextView name = mView.findViewById(R.id.CovidUser_name);
        TextView address = mView.findViewById(R.id.address);
        TextView phone = mView.findViewById(R.id.mobilenumber);
        TextView email = mView.findViewById(R.id.email);
        TextView  symptoms0= mView.findViewById(R.id.symptoms0);
        TextView  symptoms1= mView.findViewById(R.id.symptoms1);
        TextView  symptoms2= mView.findViewById(R.id.symptoms2);
        TextView  symptoms3= mView.findViewById(R.id.symptoms3);


        TextView othersymptoms0 = mView.findViewById(R.id.Othersymptoms0);
        TextView othersymptoms1 = mView.findViewById(R.id.Othersymptoms1);
        TextView othersymptoms2 = mView.findViewById(R.id.Othersymptoms2);
        TextView othersymptoms3 = mView.findViewById(R.id.Othersymptoms3);
        TextView othersymptoms4 = mView.findViewById(R.id.Othersymptoms4);
        TextView health0 = mView.findViewById(R.id.healthchecklist0);


        symptoms0.setText(logbookholder.get(position).getSymptoms());
        symptoms1.setText(logbookholder.get(position).getSymptoms1());
        symptoms2.setText(logbookholder.get(position).getSymptoms2());
        symptoms3.setText(logbookholder.get(position).getSymptoms3());

        health0.setText(logbookholder.get(position).getHealthChecklist());

        othersymptoms0.setText(logbookholder.get(position).getOtherSymptoms());
        othersymptoms1.setText(logbookholder.get(position).getOtherSymptoms1());
        othersymptoms2.setText(logbookholder.get(position).getOtherSymptoms2());
        othersymptoms3.setText(logbookholder.get(position).getOtherSymptoms3());
        othersymptoms4.setText(logbookholder.get(position).getOtherSymptoms4());


        date.setText(logbookholder.get(position).getSurveyDate());
        address.setText(logbookholder.get(position).getAddress());
        name.setText(logbookholder.get(position).getFullName());
        phone.setText(logbookholder.get(position).getPhonenumber());
        email.setText(logbookholder.get(position).getEmail());

    }



}