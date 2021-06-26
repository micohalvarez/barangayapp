package com.release.barangayapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.release.barangayapp.R;
import com.release.barangayapp.adapter.ChatsAdapter;
import com.release.barangayapp.model.Message;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.MessageService;
import com.release.barangayapp.service.UserService;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements ChatsAdapter.OnChatListener {



    private Intent intent;

    private ImageView profileImage;
    private TextView username;
    private FirebaseUser firebaseUser;
    private String chatUser;
    private AuthService authService;
    private UserService userService;

    private ImageButton buttonSend;
    private EditText messageText;

    private MessageService messageService;
    private ChatsAdapter chatsAdapter;
    private List<Message> message;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authService = new AuthService();
        userService = new UserService();
        messageService = new MessageService();


        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.message_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profileImage = findViewById(R.id.message_icon);
        username = findViewById(R.id.username);
        buttonSend = findViewById(R.id.send_chat);
        messageText = findViewById(R.id.message_text);

        recyclerView = findViewById(R.id.chat_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MessageActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        chatUser = intent.getStringExtra("userId");

        firebaseUser = authService.getAuthUser();

        userService.getUser(value -> {

            if (value != null) {

                username.setText(value.getFullName());
            }
        }, chatUser);


        buttonSend.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String message = messageText.getText().toString();
                if (!message.equals("")){
                    sendMessage(firebaseUser.getUid(),chatUser,message);
                    messageText.setText("");
                }
                else{
                    Toast.makeText(MessageActivity.this,"Please add message",Toast.LENGTH_SHORT).show();
                }
            }
        });

        initAdapter();

    }

    private void initAdapter() {

        messageService.getData(value -> {
            message = value;
            chatsAdapter = new ChatsAdapter(message,this );
            recyclerView.setAdapter(chatsAdapter);

        },firebaseUser.getUid(),chatUser);

    }



    private void sendMessage(String sender, String receiver, String message){

        Message newMessage = new Message();

        newMessage.setMessage(message);
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);


        messageService.saveData(newMessage);

    }

    @Override
    public void onChatClick(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}