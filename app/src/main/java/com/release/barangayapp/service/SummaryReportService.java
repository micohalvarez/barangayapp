package com.release.barangayapp.service;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.callback.SummaryReportCallback;
import com.release.barangayapp.model.SummaryReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SummaryReportService {

    private DatabaseReference summaryRef;
    private FirebaseDatabase firebaseDatabase;

    public SummaryReportService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        summaryRef = firebaseDatabase.getReference("summary_report");
    }

    // function for getting the data from summary_report tree
    public ArrayList<SummaryReport> getData(SummaryReportCallback myCallBack) {
        ArrayList<SummaryReport> reportList = new ArrayList<>();

        summaryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        reportList.add(dsp.getValue(SummaryReport.class));
                    }
                    myCallBack.summaryCallBack(reportList);
                } else
                    myCallBack.summaryCallBack(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return reportList;
    }

    // function for saving data to the summary_report tree
    public void saveData(SummaryReport summary, Context context){
        summaryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                    summaryRef.push().setValue(summary);
                else
                { for (DataSnapshot ds : dataSnapshot.getChildren())
                    summaryRef.child(ds.getKey()).setValue(summary);
                    Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
