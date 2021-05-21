package com.release.barangayapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.adapter.AnnouncementRecyclerViewAdapter;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.service.AnnouncementService;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.view.AdminMainMenu;
import com.release.barangayapp.view.MainMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link Fragment_Announcement#newInstance} factory method to create an
 * instance of this fragment.
 */
public class Fragment_Announcement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    private AnnouncementRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Announcement> announcementholder;
    private AnnouncementService announcementService;

    public Fragment_Announcement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the
     * provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Announcement.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Announcement newInstance(String param1, String param2) {
        Fragment_Announcement fragment = new Fragment_Announcement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /* return inflater.inflate(R.layout.fragment_announcement, container, false); */

        View view = inflater.inflate(R.layout.fragment__announcement, container, false);
        recyclerView = view.findViewById(R.id.announcement_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        announcementholder = new ArrayList<>();
        announcementService = new AnnouncementService();

        System.out.println("hehe");
        return view;
    }

    private boolean isLoading = false;
    private int startingIndex = 20;
    private int currentSize;
    private int nextLimit;
    private String finalKey = "";

    private void initAdapter() {

        announcementService.getData(value -> {
            announcementholder = value;
            System.out.println(value);
            initializeAdapter();
        });

    }

    private void initializeAdapter() {
//        recyclerViewAdapter = new AnnouncementRecyclerViewAdapter(announcementholder, );
//        recyclerView.setAdapter(recyclerViewAdapter);
    }

}