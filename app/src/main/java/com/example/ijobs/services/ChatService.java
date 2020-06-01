package com.example.ijobs.services;

import com.example.ijobs.data.Chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatService {
    public void sendMessage(String sender, String receiver, String message) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        Chat chat = new Chat(sender, receiver, message);

        db.child("chats").push().setValue(chat);
    }

    public DatabaseReference getMessages() {
        return FirebaseDatabase.getInstance().getReference("chats");
    }
}
