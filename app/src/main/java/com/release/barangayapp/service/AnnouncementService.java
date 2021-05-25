package com.release.barangayapp.service;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.model.UserRegisterObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnnouncementService {

    private DatabaseReference newsFeedRef;
    private FirebaseDatabase firebaseDatabase;
    private String finalKey;

    public AnnouncementService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        newsFeedRef = firebaseDatabase.getReference("news_feed");
    }

    //function for getting the data from news_feed tree
    public ArrayList<Announcement> getData(AnnouncementCallback myCallBack){
        ArrayList<Announcement> announcementList = new ArrayList<>();

        newsFeedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        announcementList.add(dsp.getValue(Announcement.class));
                    }
                    myCallBack.announcementCallBack(announcementList);
                }
                else
                    myCallBack.announcementCallBack(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return announcementList;
    }

    //function for getting the data from news_feed tree
    public ArrayList<Announcement> getSomeData(AnnouncementCallback myCallBack, int limit){
        ArrayList<Announcement> announcementList = new ArrayList<>();

        newsFeedRef.limitToFirst(limit).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Announcement announcement;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        announcement = dsp.getValue(Announcement.class);
                        announcement.setKey(dsp.getKey());
                        announcementList.add(announcement);
                    }

                    finalKey = announcementList.get(4).getKey();
                    System.out.println(finalKey);
                    myCallBack.announcementCallBack(announcementList);
                }
                else
                    myCallBack.announcementCallBack(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return announcementList;
    }

    //function for getting the data from news_feed tree
    public ArrayList<Announcement> loadMoreData(AnnouncementCallback myCallBack, int limit,ArrayList<Announcement> announcementList){
        newsFeedRef.limitToFirst(limit).orderByKey().startAt(finalKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Announcement announcement;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                        if(!dsp.getKey().equals(finalKey)) {
                            announcement = dsp.getValue(Announcement.class);
                            announcement.setKey(dsp.getKey());
                            announcementList.add(announcement);
                        }
                    }
                    finalKey = announcementList.get(announcementList.size() - 1).getKey();
                    myCallBack.announcementCallBack(announcementList);
                }
                else
                    myCallBack.announcementCallBack(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return announcementList;
    }


    //function for saving data to the news_feed tree
    public void saveData(Announcement announcement){
        newsFeedRef.push().setValue(announcement);
    }

    //function for deleting data to the news_feed tree
    public void deleteData(String newFeedId){
        newsFeedRef.child(newFeedId).removeValue();
    }

}
