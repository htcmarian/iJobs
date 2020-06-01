package com.example.ijobs.activities.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ijobs.R;
import com.example.ijobs.activities.chat.fragments.ChatMessageAdapter;
import com.example.ijobs.data.Chat;
import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.ChatService;
import com.example.ijobs.services.UserService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SendMessageActivity extends AppCompatActivity {

    private ImageView sendMessageButton;
    private EditText messageInput;
    private TextView userToChatWith;
    private String userToChatWithId;
    private String currentUserId;
    private ChatService chatService;
    private ChatMessageAdapter chatMessageAdapter;
    private List<Chat> chats;
    private RecyclerView chatMessageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        chatService = new ChatService();

        initializeComponents();

        readMessages(currentUserId, userToChatWithId);
    }

    private void initializeComponents() {
        sendMessageButton = findViewById(R.id.send_message_sendTextButton);
        messageInput = findViewById(R.id.send_message_inputTextField);
        userToChatWith = findViewById(R.id.send_message_userToChatTextView);
        userToChatWithId = getIntent().getStringExtra("userToChatWithId");
        currentUserId = getIntent().getStringExtra("userId");

        chatMessageContainer = findViewById(R.id.send_message_recyclerView);
        chatMessageContainer.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chatMessageContainer.setLayoutManager(linearLayoutManager);


        sendMessageButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();

            if (!message.isEmpty()) {
                chatService.sendMessage(currentUserId, userToChatWithId, message);
            } else {
                Toast.makeText(SendMessageActivity.this, "You cannot send an empty message", Toast.LENGTH_SHORT).show();
            }

            messageInput.setText("");
        });

    }

    private void readMessages(String currentUserId, String userToChatWithId) {
        chats = new ArrayList<>();

        chatService.getMessages().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = messageSnapshot.getValue(Chat.class);
                    boolean isConversationBetweenTargetUsers = chat.getReceiver().equals(currentUserId) && chat.getSender().equals(userToChatWithId) ||
                            chat.getReceiver().equals(userToChatWithId) && chat.getSender().equals(currentUserId);

                    if (isConversationBetweenTargetUsers) {
                        chats.add(chat);
                    }

                    chatMessageAdapter = new ChatMessageAdapter(SendMessageActivity.this, chats, currentUserId);
                    chatMessageContainer.setAdapter(chatMessageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
