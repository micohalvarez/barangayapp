package com.release.barangayapp.service;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.model.LogBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogBookService {

    private DatabaseReference logbookRef;
    private FirebaseDatabase firebaseDatabase;
    private String key;

    public LogBookService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        logbookRef = firebaseDatabase.getReference("logbook");
    }

    // function for getting the data from logbook tree
    public ArrayList<LogBook> getData() {

        ArrayList<LogBook> logBookList = new ArrayList<>();

        logbookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    logBookList.add(dsp.getValue(LogBook.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return logBookList;
    }

    // function for saving data to the logbook tree
    public void saveData(LogBook logbookData/* String userID */) {
        logbookRef.push().setValue(logbookData);
        // Query query = logbookRef.orderByChild("userID").equalTo(userID);
        //
        // query.addValueEventListener(new ValueEventListener() {
        // @Override
        // public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // if (dataSnapshot.exists())
        // {
        // if ( dataSnapshot.getKey().isEmpty()) {
        // logbookRef.push().setValue(logbookData);
        // }
        // else
        // {
        // logbookRef.child(dataSnapshot.getKey()).setValue(logbookData);
        // }
        // }
        //
        //
        // }
        //
        // @Override
        // public void onCancelled(@NonNull DatabaseError databaseError) {
        //
        // }
        // });

    }

    // function for deleting data to the logbook tree
    public void deleteData() {

    }

}
