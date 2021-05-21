package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.model.LogBook;
import com.release.barangayapp.model.SummaryReport;
import com.release.barangayapp.model.UpdateReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateReportService {
    private DatabaseReference updateRef;
    private FirebaseDatabase firebaseDatabase;

    public UpdateReportService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        updateRef = firebaseDatabase.getReference("update_report");
    }

    //function for getting the data from logbook tree
    public ArrayList<UpdateReport> getData(){

        ArrayList<UpdateReport> reportList = new ArrayList<>();

        updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    reportList.add(dsp.getValue(UpdateReport.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return reportList;
    }

    //function for saving data to the logbook tree
    public void saveData(LogBook logbookData){
        updateRef.push().setValue(logbookData);
    }

    //function for deleting data to the logbook tree
    public void deleteData(){
    }
}
