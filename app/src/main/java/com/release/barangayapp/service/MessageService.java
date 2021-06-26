package com.release.barangayapp.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.release.barangayapp.callback.MessageCallback;
import com.release.barangayapp.callback.UserListCallback;
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
    public void getData(MessageCallback myCallBack,String senderId,String receiverId){


        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Message> messageList = new ArrayList<>();
                Message message;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        message = dsp.getValue(Message.class);
                        if(message.getSender().equals(senderId) && message.getReceiver().equals(receiverId))
                             messageList.add(dsp.getValue(Message.class));

                        if(message.getReceiver().equals(senderId)  && message.getSender().equals(receiverId))
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

    }

    private String userFinalKey;

    //function for getting the data from chat tree
    public void getReceiverUsers(UserListCallback myCallBack, String senderId){
        ArrayList<String> receiverList = new ArrayList<>();

        messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Message message;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        message = dsp.getValue(Message.class);
                        userFinalKey = dsp.getKey();

                        if(message.getSender().equals(senderId)) {
                            receiverList.add(message.getReceiver());

                        }
                        if (message.getReceiver().equals(senderId)) {
                            receiverList.add(message.getSender());
                        }

                    }

                    myCallBack.usersCallback(receiverList);
                }
                else
                    myCallBack.usersCallback(null);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void loadMoreUsers(UserListCallback myCallBack, int limit,String senderId, ArrayList<String> usersList){

        messageRef.limitToFirst(limit).orderByKey().startAt(userFinalKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        if (!dsp.getKey().equals(userFinalKey)) {
                            message = dsp.getValue(Message.class);
                            userFinalKey = dsp.getKey();

                            if (message.getSender().equals(senderId)) {
                                usersList.add(message.getReceiver());
                            }
                            if (message.getReceiver().equals(senderId)) {
                                usersList.add(message.getSender());
                            }
                        }
                    }
                    myCallBack.usersCallback(usersList);
                }
                else
                    myCallBack.usersCallback(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    //function for saving data to the chat tree
    public void saveData(Message message){
        messageRef.push().setValue(message);
    }
}
