package com.release.barangayapp.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.release.barangayapp.model.UserRegisterObject;

public class AuthService {

    private FirebaseUser user;
    private UserRegisterObject userObject;
    public AuthService(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        userObject = new UserRegisterObject();
    }

    public FirebaseUser getAuthUser(){
        return user;
    }

    public void signout(){
        FirebaseAuth.getInstance().signOut();
    }

    public void getUserDetails(MyCallBack myCallBack) {
        if(user !=  null) {
            Log.e("USER","NOT NULL");
            FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        myCallBack.authCallBack(userObject);
                    } else {
                        userObject = task.getResult().getValue(UserRegisterObject.class);
                        myCallBack.authCallBack(userObject);

                    }
                }
            });
        }
        else
            myCallBack.authCallBack(null);
    }
}
