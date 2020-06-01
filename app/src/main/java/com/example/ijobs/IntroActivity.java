package com.example.ijobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.ijobs.data.User;
import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.UserService;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class IntroActivity extends AppCompatActivity {

    private AuthService authService;
    private UserService userService;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initializeComponents();

        authService = new AuthService();
        userService = new UserService();

        redirectToActivity();
    }

    private void initializeComponents() {
        loadingSpinner = findViewById(R.id.mainActivity_loadingSpinner);
    }

    private void redirectToActivity() {
        if (authService.getUser() == null) {
            Intent authActivityIntent = new Intent(this, AuthActivity.class);
            authActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(authActivityIntent);
        } else {
            userService.getCurrentUser().addOnCompleteListener(command -> {
                if (command.isSuccessful()) {
                    User user = command.getResult().toObject(User.class);

                    if (user.getUserProfile() == null) {
                        Intent createUserProfileActivity = new Intent(this, CreateUserProfileActivity.class);
                        createUserProfileActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(createUserProfileActivity);
                    } else {
                        Intent mainActivityIntent = new Intent(this, MainActivity.class);
                        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(mainActivityIntent);
                    }
                }
            });
        }
    }
}
