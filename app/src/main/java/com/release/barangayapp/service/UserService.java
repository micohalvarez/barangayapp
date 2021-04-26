package com.release.barangayapp.service;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.Register;
import com.release.barangayapp.UserLogin;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.model.UserObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private DatabaseReference newsFeedRef;
    private FirebaseDatabase firebaseDatabase;

    public UserService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        newsFeedRef = firebaseDatabase.getReference("users");
    }

    //function for getting the data from news_feed tree
    public ArrayList<UserObject> getData(){
        ArrayList<UserObject> usersList = new ArrayList<>();

        newsFeedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    usersList.add(dsp.getValue(UserObject.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return usersList;
    }

    //function for saving data to the news_feed tree
    public void saveData(UserObject userData,String userId){

        newsFeedRef.child(userId).setValue(userData);
    }

    //function for deleting data to the news_feed tree
    public void deleteData(String newFeedId){
        newsFeedRef.child(newFeedId).removeValue();
    }

}
