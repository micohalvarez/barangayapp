package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.callback.LogBookCallback;
import com.release.barangayapp.model.LogBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogBookService {

    private DatabaseReference logbookRef;
    private FirebaseDatabase firebaseDatabase;
    private String finalKey;

    public LogBookService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        logbookRef = firebaseDatabase.getReference("logbook");
    }

    // function for getting the data from logbook tree
    public ArrayList<LogBook> getData(LogBookCallback myCallBack, int limit){

        ArrayList<LogBook> logBookList = new ArrayList<>();

        logbookRef.limitToFirst(limit).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LogBook logBook;
                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        logBook = dsp.getValue(LogBook.class);
                        logBook.setKey(dsp.getKey());
                        logBookList.add(logBook);
                    }
                    finalKey = logBookList.get(logBookList.size() - 1).getKey();
                    myCallBack.logBookCallback(logBookList);
                }
                else
                    myCallBack.logBookCallback(null);
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
    public ArrayList<LogBook> loadMoreData(LogBookCallback myCallBack, int limit, ArrayList<LogBook> logBookArrayList){
        logbookRef.limitToFirst(limit).orderByKey().startAt(finalKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LogBook logBook;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                        if(!dsp.getKey().equals(finalKey)) {
                            logBook = dsp.getValue(LogBook.class);
                            logBook.setKey(dsp.getKey());
                            logBookArrayList.add(logBook);
                        }
                        else break;
                    }
                    /*Collections.reverse(announcementList);*/
                    finalKey = logBookArrayList.get(logBookArrayList.size() - 1).getKey();
                    myCallBack.logBookCallback(logBookArrayList);
                }
                else
                    myCallBack.logBookCallback(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return logBookArrayList;
    }


}
