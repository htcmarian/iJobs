package com.example.ijobs.repositories;

import com.example.ijobs.data.User;
import com.example.ijobs.data.JobSeekerCv;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {
    public Task<Void> createUser(String userId, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        User user = new User(userId, email);

        return db.collection("users").document(userId).set(user);
    }

    public Task<Void> updateCv(String userId, JobSeekerCv jobSeekerCv){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("users").document(userId).update("jobSeekerCv", jobSeekerCv);
    }

    public Task<DocumentSnapshot> getUser(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("users").document(userId).get();
    }
}
