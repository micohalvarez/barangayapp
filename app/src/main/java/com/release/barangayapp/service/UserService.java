package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.callback.SingleUserCallback;
import com.release.barangayapp.callback.UserCallback;
import com.release.barangayapp.model.UserObject;

import java.util.ArrayList;

public class UserService {

    private DatabaseReference usersRef;
    private FirebaseDatabase firebaseDatabase;
    private String finalKey;


    public UserService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference("users");
    }

    //function for getting the data from news_feed tree
    public void getData(UserCallback myCallBack){
        ArrayList<UserObject> usersList = new ArrayList<>();

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    //function for getting the data from news_feed tree
    public void getUser(SingleUserCallback myCallBack, String userId){
        ArrayList<UserObject> usersList = new ArrayList<>();
        System.out.println(userId);
        usersRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserObject user = dataSnapshot.getValue(UserObject.class);
                System.out.println(user.getFullName());
                myCallBack.usersCallback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                myCallBack.usersCallback(null);
            }
        });

        myCallBack.usersCallback(null);
    }

    public void getChatUsers(UserCallback myCallBack,ArrayList<String> userIds){

        ArrayList<UserObject> userObjects = new ArrayList<>();

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        UserObject user = dsp.getValue(UserObject.class);
                        user.setUserId(dsp.getKey());
                    for(String id : userIds){
                        if(user.getUserId().equals(id)) {
                            if (userObjects.size() != 0) {

                                if(!userObjects.contains(user))
                                    userObjects.add(user);

                            } else {
                                userObjects.add(user);
                            }
                        }
                    }

                }

                finalKey = userObjects.get(userObjects.size() - 1).getUserId();
                myCallBack.userCallback(userObjects);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                myCallBack.userCallback(userObjects);
            }
        });

        myCallBack.userCallback(userObjects);
    }



    public void getAdminUsers(UserCallback myCallBack){

        ArrayList<UserObject> userObjects = new ArrayList<>();

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    UserObject user = dsp.getValue(UserObject.class);
                    user.setUserId(dsp.getKey());

                    if (user.getRole() == 1) {
                        userObjects.add(user);
                    }

                }
                finalKey = userObjects.get(userObjects.size() - 1).getUserId();
                myCallBack.userCallback(userObjects);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                myCallBack.userCallback(userObjects);
            }
        });

        myCallBack.userCallback(userObjects);
    }

    public void loadMoreAdminUsers(UserCallback myCallBack, int limit,ArrayList<UserObject> usersList){
        usersRef.limitToFirst(limit).orderByKey().startAt(finalKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        if(!dsp.getKey().equals(finalKey)) {
                            UserObject user = dsp.getValue(UserObject.class);
                            user.setUserId(dsp.getKey());
                            if (user.getRole() == 1) {
                                usersList.add(user);
                            }
                        }
                        else
                            break;

                    }
                    finalKey = usersList.get(usersList.size() - 1).getUserId();
                    myCallBack.userCallback(usersList);
                }
                else
                    myCallBack.userCallback(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    public void getMoreChatUsers(UserCallback myCallBack, int limit,ArrayList<String> userIds, ArrayList<UserObject> usersList){
        usersRef.limitToFirst(limit).orderByKey().startAt(finalKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        UserObject user = dsp.getValue(UserObject.class);
                        if(!dsp.getKey().equals(finalKey)) {
                            user.setUserId(dsp.getKey());
                            for (String id : userIds) {
                                if (user.getUserId().equals(id)) {
                                    if (usersList.size() != 0) {
                                        for (UserObject user1 : usersList) {
                                            if (!user.getUserId().equals((user1.getUserId())))
                                                usersList.add(user);
                                        }
                                    } else {
                                        usersList.add(user);
                                    }
                                }
                            }
                        }
                        else break;
                    }
                    finalKey = usersList.get(usersList.size() - 1).getUserId();
                    myCallBack.userCallback(usersList);
                }
                else
                    myCallBack.userCallback(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    //function for saving data to the news_feed tree
    public void saveData(UserObject userData, String userId){

        usersRef.child(userId).setValue(userData);
    }

    //function for deleting data to the news_feed tree
    public void deleteData(String newFeedId){
        usersRef.child(newFeedId).removeValue();
    }

}
