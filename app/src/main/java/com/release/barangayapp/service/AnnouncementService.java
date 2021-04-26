package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.model.Announcement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnnouncementService {

    private DatabaseReference newsFeedRef;
    private FirebaseDatabase firebaseDatabase;

    public AnnouncementService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        newsFeedRef = firebaseDatabase.getReference("news_feed");
    }

    //function for getting the data from news_feed tree
    public ArrayList<Announcement> getData(){
        ArrayList<Announcement> announcementList = new ArrayList<>();

        newsFeedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                   announcementList.add(dsp.getValue(Announcement.class));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return announcementList;
    }

    //function for saving data to the news_feed tree
    public void saveData(Announcement logbookData){
        newsFeedRef.push().setValue(logbookData);
    }

    //function for deleting data to the news_feed tree
    public void deleteData(String newFeedId){
        newsFeedRef.child(newFeedId).removeValue();
    }

}
