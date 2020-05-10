package com.example.ijobs.services;

import com.example.ijobs.data.JobSeekerCv;
import com.example.ijobs.repositories.UserRepository;
import com.example.ijobs.viewmodels.JobSeekerProfileViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;

    public UserService(){
        authService = new AuthService();
        userRepository = new UserRepository();
    }

    public Task<Void> updateJobSeekerCv(JobSeekerProfileViewModel model){
        FirebaseUser user = authService.getUser();

        if(user != null){
            List<String> servicesOffered = model.getServicesOffered().stream().map(x->x.getServiceName()).collect(Collectors.toList());

            return userRepository.updateCv(user.getUid(), new JobSeekerCv(model.getName(), model.getBirthDate(), model.getAddress(), model.getPostalCode(), model.getCity(), model.getPhoneNumber(), servicesOffered));
        }

        return null;
    }

    public Task<Void> createUser(String userId, String email) {
        return userRepository.createUser(userId, email);
    }

    public Task<DocumentSnapshot> getCurrentUser() {
        FirebaseUser user = authService.getUser();

        if(user != null){
            return userRepository.getUser(user.getUid());
        }

        return null;
    }
}
