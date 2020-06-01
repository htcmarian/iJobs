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

public class MainActivity extends AppCompatActivity {

    private UserService userService;
    private ImageView logoutButton;
    private AuthService authService;
    private ImageView conversationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = new UserService();
        authService = new AuthService();

        initializeComponents();
    }

    private void initializeComponents() {
        logoutButton = findViewById(R.id.mainActivity_logoutButton);
        conversationButton = findViewById(R.id.mainActivity_conversationButton);

        logoutButton.setOnClickListener(v -> {
            authService.logout();
            navigateToLoginScreen();
        });

        conversationButton.setOnClickListener(v -> {
            navigateToConversationScreen();
        });
    }

    private void navigateToLoginScreen() {
        Intent authActivity = new Intent(MainActivity.this, AuthActivity.class);
        authActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(authActivity);
    }

    private void navigateToConversationScreen(){
        Intent listMessagesActivity = new Intent(MainActivity.this, ListMessagesActivity.class);
        listMessagesActivity.putExtra("currentUserId", authService.getUser().getUid());
        startActivity(listMessagesActivity);
    }

    public void onJobSeekerButtonClick(View view) {
        userService
                .getCurrentUser()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    if(user.getJobSeekerCv() == null){
                        Intent createJobSeekerProfileActivity = new Intent(this, CreateJobSeekerProfileActivity.class);
                        startActivity(createJobSeekerProfileActivity);
                    }
                    else{
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
