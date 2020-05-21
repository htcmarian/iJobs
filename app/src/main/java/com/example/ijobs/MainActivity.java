package com.example.ijobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ijobs.activities.jobRecruiter.JobRecruiterListingScreen;
import com.example.ijobs.activities.jobSeeker.CreateJobSeekerProfileActivity;
import com.example.ijobs.activities.jobSeeker.JobSeekerListingScreen;
import com.example.ijobs.data.User;
import com.example.ijobs.services.UserService;

public class MainActivity extends AppCompatActivity {

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = new UserService();
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
