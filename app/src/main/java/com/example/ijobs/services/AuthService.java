package com.example.ijobs.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthService {

    public Task<AuthResult> register(String email, String password) {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password);
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public Task<AuthResult> login(String email, String password) {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
    }
}
