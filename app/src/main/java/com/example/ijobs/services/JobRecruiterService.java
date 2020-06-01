package com.example.ijobs.services;

import com.example.ijobs.data.JobPost;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class JobRecruiterService {

    private final AuthService authService;

    public JobRecruiterService(){
        authService = new AuthService();
    }

    public Query getJobSeekers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("users").whereEqualTo("hasJobSeekerCv", true);
    }

    public Task<Void> createJobPost(JobPost ad) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = java.util.UUID.randomUUID().toString();

        String createdByUserId = authService.getUser().getUid();
        ad.setCreatedBy(createdByUserId);
        ad.setId(id);

        return db.collection("jobPosts").document(id).set(ad);
    }
}
