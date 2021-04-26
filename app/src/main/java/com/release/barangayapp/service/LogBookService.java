package com.release.barangayapp.service;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.release.barangayapp.model.LogBook;

import java.util.ArrayList;

public class LogBookService {

    private DatabaseReference logbookRef;
    private FirebaseDatabase firebaseDatabase;

    public LogBookService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        logbookRef = firebaseDatabase.getReference("logbook");
    }


    //function for getting the data from logbook tree
    public ArrayList<LogBook> getData(){

        return null;
    }

    //function for getting the data from logbook tree given a user ID
    public ArrayList<LogBook> getMyData(){


        return null;
    }

    //function for saving data to the logbook tree
    public void saveData(LogBook logbookData){

        logbookRef.push().setValue(logbookData);

    }

    //function for deleting data to the logbook tree
    public void deleteData(){

    }


}
