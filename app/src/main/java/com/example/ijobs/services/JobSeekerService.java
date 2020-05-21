package com.example.ijobs.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class JobSeekerService {
    private final UserService userService;

    public JobSeekerService() {
        userService = new UserService();
    }

    public Task<QuerySnapshot> getRecommendedJobPosts(String filterJobType, String filterServiceType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query filteredQuery = db.collection("jobPosts").limit(100);

        if (filterJobType != null && !filterJobType.isEmpty()) {
            filteredQuery = filteredQuery.whereEqualTo("jobType", filterJobType);
        }
        if (filterServiceType != null && !filterServiceType.isEmpty()) {
            filteredQuery = filteredQuery.whereEqualTo("serviceRequired", filterServiceType);
        }

        return filteredQuery.get();

    }
}
