package com.example.ijobs.services;

import com.example.ijobs.data.JobSeekerCv;
import com.example.ijobs.data.UserProfile;
import com.example.ijobs.repositories.UserRepository;
import com.example.ijobs.viewmodels.JobSeekerProfileViewModel;
import com.example.ijobs.viewmodels.UserProfileViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;

    public UserService() {
        authService = new AuthService();
        userRepository = new UserRepository();
    }

    public Task<Void> updateUserProfile(UserProfileViewModel model) {
        FirebaseUser user = authService.getUser();

        if (user != null) {
            return userRepository.createUserProfile(user.getUid(), new UserProfile(model.getName(), model.getBirthDate(), model.getAddress(), model.getPostalCode(), model.getCity(), model.getPhoneNumber()));
        }

        return null;
    }

    public Task<Void> createUser(String userId, String email) {
        return userRepository.createUser(userId, email);
    }

    public Task<DocumentSnapshot> getCurrentUser() {
        FirebaseUser user = authService.getUser();

        if (user != null) {
            return userRepository.getUser(user.getUid());
        }

        return null;
    }

    public Task<Void> updateJobSeekerProfile(JobSeekerProfileViewModel jobSeekerProfileViewModel) {
        return userRepository.updateJobSeekerProfile(new JobSeekerCv(jobSeekerProfileViewModel.getUserId(), jobSeekerProfileViewModel.getPastExperience(), jobSeekerProfileViewModel.getPastJobs(), jobSeekerProfileViewModel.getServicesOffered(), jobSeekerProfileViewModel.getSkills()));
    }

    public Task<DocumentSnapshot> getUserDetails(String createdBy) {
        return userRepository.getUser(createdBy);
    }

    public Task<QuerySnapshot> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
