package com.release.barangayapp.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.release.barangayapp.callback.NotificationCallBack;
import com.release.barangayapp.singleton.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

;

public class NotificationService extends FirebaseMessagingService {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference deviceTokenRef;
    private String token;
    private boolean hasToken = false;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=AAAAYmizxWY:APA91bEzcMNibPlOmS3-Qv_6Eog3hznQVtiJRDW85mpaVrYQ7W-NezqwxelYAYXzbrsRyN66t1VU36U64s3EUFY6SW0O9tIJCl2SN2oIiDcrg2XBLWX2ThYc-KzYR1Y1fD5hRtt0DMzx";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    public NotificationService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        deviceTokenRef = firebaseDatabase.getReference("device_token");
        token = "";

    }

    public void getToken(NotificationCallBack myCallBack){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Error", "Fetching FCM registration token failed", task.getException());
                            myCallBack.notificationCallBack(false);
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        hasToken = true;
                        myCallBack.notificationCallBack(true);
                    }
                });

    }


    public void pushToken(NotificationCallBack myCallBack){


            deviceTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                    boolean isExist = false;
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            String newToken = dsp.getValue(String.class);
                            if (token.equals(newToken))
                                isExist = true;
                        }
                        if (!isExist) {
                            deviceTokenRef.push().setValue(token);
                        }
                        myCallBack.notificationCallBack(true);
                    } else
                        myCallBack.notificationCallBack(false);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

    }


    public void sendNotif(NotificationCallBack myCallBack,String title, String message, Context context){

        deviceTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                JSONObject notificationBody = new JSONObject();
                JSONObject notification = new JSONObject();
                try {
                    notificationBody.put("title",title);
                    notificationBody.put("body",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        String token = dsp.getValue(String.class);
                        try {
                            notification.put("to",token);
                            notification.put("notification",notificationBody);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Log.i(TAG, "onResponse: " + response.toString());

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i(TAG, "onResponse: " + error.toString());
                                        myCallBack.notificationCallBack(false);
                                    }
                                }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Authorization", serverKey);
                                params.put("Content-Type", contentType);
                                return params;
                            }
                        };
                        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                    }
                    Toast.makeText( context,"Successfully sent help.", Toast.LENGTH_SHORT).show();
                    myCallBack.notificationCallBack(true);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                myCallBack.notificationCallBack(false);
            }
        });

    }

}
