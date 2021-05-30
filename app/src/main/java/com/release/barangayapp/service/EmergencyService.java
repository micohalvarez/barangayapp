package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.callback.EmergencyCalllback;
import com.release.barangayapp.model.Emergency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmergencyService {

    private DatabaseReference emergencyRef;
    private FirebaseDatabase firebaseDatabase;
    private String finalKey;

    public EmergencyService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        emergencyRef = firebaseDatabase.getReference("emergency");
    }

    //function for getting the data from emergency tree
    public ArrayList<Emergency> getData(EmergencyCalllback myCallBack, int limit){
        ArrayList<Emergency> emergencyList = new ArrayList<>();

        emergencyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Emergency emergency;
                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        emergency = dsp.getValue(Emergency.class);
                        emergency.setKey(dsp.getKey());
                        emergencyList.add(emergency);
                    }
                    finalKey = emergencyList.get(emergencyList.size() - 1).getKey();
                    myCallBack.emergencyCallback(emergencyList);
                }
                else
                    myCallBack.emergencyCallback(null);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return emergencyList;
    }

    //function for saving data to the emergency tree
    public void saveData(Emergency emergency){
        emergencyRef.push().setValue(emergency);
    }

    public void markAsClaimed(Emergency emergency, String key){
        emergency.setFinished(true);
        emergencyRef.child(key).setValue(emergency);
    }
}
