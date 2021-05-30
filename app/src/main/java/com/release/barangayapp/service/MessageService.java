package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.callback.MessageCallback;
import com.release.barangayapp.model.Message;

import java.util.ArrayList;

public class MessageService {

    private DatabaseReference messageRef;
    private FirebaseDatabase firebaseDatabase;
    public MessageService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        messageRef = firebaseDatabase.getReference("chats");
    }

    //function for getting the data from chat tree
    public ArrayList<Message> getData(MessageCallback myCallBack,String senderId, String receiverId){
        ArrayList<Message> messageList = new ArrayList<>();

        messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Message message;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        message = dsp.getValue(Message.class);
                        if(message.getReceiver().equals(receiverId) && message.getSender().equals(senderId))
                             messageList.add(dsp.getValue(Message.class));
                    }
                    myCallBack.messageCallBack(messageList);
                }
                else
                    myCallBack.messageCallBack(null);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return messageList;
    }

    //function for saving data to the chat tree
    public void saveData(Message message){
        messageRef.push().setValue(message);

    }
}
