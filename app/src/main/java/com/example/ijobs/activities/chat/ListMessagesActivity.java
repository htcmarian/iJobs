package com.example.ijobs.activities.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;

import com.example.ijobs.R;
import com.example.ijobs.activities.chat.fragments.ChatMessageAdapter;
import com.example.ijobs.activities.chat.fragments.ListMessageAdapter;
import com.example.ijobs.data.Chat;
import com.example.ijobs.data.User;
import com.example.ijobs.services.ChatService;
import com.example.ijobs.services.UserService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMessagesActivity extends AppCompatActivity {

    private ChatService chatService;
    private UserService userService;
    private HashMap<String, String> latestChats;
    private String currentUserId;
    private ListMessageAdapter listMessageAdapter;
    private RecyclerView listMessageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messages);

        chatService = new ChatService();
        userService = new UserService();

        initializeComponents();

        loadData();
    }

    private void loadData() {
        userService.getAllUsers().addOnSuccessListener(command -> {
            List<User> allUsers = command.toObjects(User.class);
            HashMap<String, String> userIdToNameMapping = new HashMap<>();

            for(User user : allUsers){
                userIdToNameMapping.put(user.getUserId(), user.getUserProfile().getName());
            }

            getLatestConversations(userIdToNameMapping);
        });
    }

    private void getLatestConversations(HashMap<String, String> userIdToNameMapping) {
        latestChats = new HashMap<>();

        chatService.getMessages().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = messageSnapshot.getValue(Chat.class);
                    if (chat.getSender().equals(currentUserId)) {
                        latestChats.put(chat.getReceiver(), chat.getMessage());
                    }
                    if (chat.getReceiver().equals(currentUserId)) {
                        latestChats.put(chat.getSender(), chat.getMessage());
                    }
                }

                listMessageAdapter = new ListMessageAdapter(ListMessagesActivity.this, latestChats, userIdToNameMapping, currentUserId);
                listMessageContainer.setAdapter(listMessageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeComponents() {
        currentUserId = getIntent().getStringExtra("currentUserId");

        listMessageContainer = findViewById(R.id.list_messages_recycler_view);

        listMessageContainer.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        listMessageContainer.setLayoutManager(linearLayoutManager);
    }


}
