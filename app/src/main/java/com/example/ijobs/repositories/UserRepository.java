package com.example.ijobs.repositories;

import com.example.ijobs.data.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {
    public Task<Void> createUser(String userId, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        User user = new User(userId, email);

        return db.collection("users").document(userId).set(user);
    }
}
