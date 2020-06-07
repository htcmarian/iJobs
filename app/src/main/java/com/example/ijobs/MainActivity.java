package com.example.ijobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ijobs.activities.chat.ListMessagesActivity;
import com.example.ijobs.activities.jobRecruiter.JobRecruiterListingScreen;
import com.example.ijobs.activities.jobSeeker.CreateJobSeekerProfileActivity;
import com.example.ijobs.activities.jobSeeker.JobSeekerListingScreen;
import com.example.ijobs.data.User;
import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.UserService;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private UserService userService;
    private AuthService authService;
    private FloatingActionButton conversationButton;
    private BottomAppBar materialToolbar;
    private ImageView jobSeekerButton;
    private ImageView jobRecruiterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = new UserService();
        authService = new AuthService();

        initializeComponents();
    }

    private void initializeComponents() {
        materialToolbar = findViewById(R.id.mainActivity_BottomBar);
        conversationButton = findViewById(R.id.mainActivity_chatConversationButton);
        jobSeekerButton = findViewById(R.id.mainActivity_jobSeekerButton);
        jobRecruiterButton = findViewById(R.id.mainActivity_jobRecruiterButton);

        materialToolbar.setNavigationOnClickListener(v -> {
            authService.logout();
            navigateToLoginScreen();
        });

        conversationButton.setOnClickListener(v -> {
            navigateToConversationScreen();
        });

        jobSeekerButton.setOnClickListener(this::onJobSeekerButtonClick);
        jobRecruiterButton.setOnClickListener(this::onJobRecruiterButtonClick);
    }

    private void navigateToLoginScreen() {
        Intent authActivity = new Intent(MainActivity.this, AuthActivity.class);
        authActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(authActivity);
    }

    private void navigateToConversationScreen() {
        Intent listMessagesActivity = new Intent(MainActivity.this, ListMessagesActivity.class);
        listMessagesActivity.putExtra("currentUserId", authService.getUser().getUid());
        startActivity(listMessagesActivity);
    }

    public void onJobSeekerButtonClick(View view) {
        userService
                .getCurrentUser()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    if (user.getJobSeekerCv() == null) {
                        Intent createJobSeekerProfileActivity = new Intent(this, CreateJobSeekerProfileActivity.class);
                        startActivity(createJobSeekerProfileActivity);
                    } else {
                        Intent jobSeekerListingScreenActivity = new Intent(this, JobSeekerListingScreen.class);
                        startActivity(jobSeekerListingScreenActivity);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "eroare", Toast.LENGTH_LONG);
                });
    }

    public void onJobRecruiterButtonClick(View view) {
        Intent intent = new Intent(this, JobRecruiterListingScreen.class);
        startActivity(intent);
    }
}
