package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.callback.UserCallback;
import com.release.barangayapp.model.UserObject;

import java.util.ArrayList;

public class UserService {

    private DatabaseReference newsFeedRef;
    private FirebaseDatabase firebaseDatabase;

    public UserService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        newsFeedRef = firebaseDatabase.getReference("users");
    }

    //function for getting the data from news_feed tree
    public void getData(UserCallback myCallBack){
        ArrayList<UserObject> usersList = new ArrayList<>();

        newsFeedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    usersList.add(dsp.getValue(UserObject.class));
                }
                myCallBack.userCallback(usersList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                myCallBack.userCallback(usersList);
            }
        });

        myCallBack.userCallback(usersList);
    }

    //function for saving data to the news_feed tree
    public void saveData(UserObject userData, String userId){

        newsFeedRef.child(userId).setValue(userData);
    }

    //function for deleting data to the news_feed tree
    public void deleteData(String newFeedId){
        newsFeedRef.child(newFeedId).removeValue();
    }

}
