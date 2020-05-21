package com.example.ijobs.repositories;

import androidx.annotation.NonNull;

import com.example.ijobs.data.JobSeekerCv;
import com.example.ijobs.data.User;
import com.example.ijobs.data.UserProfile;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    public Task<Void> createUser(String userId, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        User user = new User(userId, email);

        return db.collection("users").document(userId).set(user);
    }

    public Task<Void> createUserProfile(String userId, UserProfile userProfile) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("users").document(userId).update("userProfile", userProfile);
    }

    public Task<DocumentSnapshot> getUser(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("users").document(userId).get();
    }

    public Task<Void> updateJobSeekerProfile(JobSeekerCv jobSeekerCv) {
        Map<String, Object> updateSchema = new HashMap<>();
        updateSchema.put("jobSeekerCv", jobSeekerCv);
        updateSchema.put("hasJobSeekerCv", true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection("users")
                .document(jobSeekerCv.getUserId())
                .update(updateSchema);
    }
}
