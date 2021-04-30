package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.model.UserRegisterObject;

import java.util.ArrayList;

public class UserService {

    private DatabaseReference newsFeedRef;
    private FirebaseDatabase firebaseDatabase;

    public UserService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        newsFeedRef = firebaseDatabase.getReference("users");
    }

    //function for getting the data from news_feed tree
    public ArrayList<UserRegisterObject> getData(){
        ArrayList<UserRegisterObject> usersList = new ArrayList<>();

        newsFeedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    usersList.add(dsp.getValue(UserRegisterObject.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return usersList;
    }
    //function for saving data to the news_feed tree
    public void saveData(UserRegisterObject userData, String userId){

        newsFeedRef.child(userId).setValue(userData);
    }

    //function for deleting data to the news_feed tree
    public void deleteData(String newFeedId){
        newsFeedRef.child(newFeedId).removeValue();
    }

}
