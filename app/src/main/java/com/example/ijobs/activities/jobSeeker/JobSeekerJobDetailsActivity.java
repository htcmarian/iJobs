package com.example.ijobs.activities.jobSeeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ijobs.R;
import com.example.ijobs.activities.chat.SendMessageActivity;
import com.example.ijobs.data.User;
import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.JobSeekerService;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.JobPostViewModel;

import java.util.List;

public class JobSeekerJobDetailsActivity extends AppCompatActivity {

    private JobSeekerService jobSeekerService;
    private UserService userService;

    private JobPostViewModel data;
    private TextView jobDescriptionTextView;
    private TextView cityTextView;
    private TextView jobTypeTextView;
    private TextView serviceRequiredTextView;
    private TextView skillsRequiredTextView;
    private TextView addedByTextView;
    private ProgressBar loadingSpinner;
    private Button contactButton;
    private ImageView locationIcon;
    private ImageView jobTypeIcon;
    private ImageView serviceRequiredIcon;
    private String addedByUserId;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_job_details);

        jobSeekerService = new JobSeekerService();
        userService = new UserService();
        authService = new AuthService();

        initializeComponents();

        loadJobDetails();
    }

    public void onContactButtonClick(View view){
        Intent goToJobSeekerListingScreen = new Intent(this, SendMessageActivity.class);
        goToJobSeekerListingScreen.putExtra("userToChatWithId", addedByUserId);
        goToJobSeekerListingScreen.putExtra("userId", authService.getUser().getUid());
        startActivity(goToJobSeekerListingScreen);
    }

    private void initializeComponents() {
        jobDescriptionTextView = findViewById(R.id.job_seeker_job_details_jobDescription);
        cityTextView = findViewById(R.id.job_seeker_job_details_city);
        jobTypeTextView = findViewById(R.id.job_seeker_job_details_jobType);
        serviceRequiredTextView = findViewById(R.id.job_seeker_job_details_serviceRequired);
        skillsRequiredTextView = findViewById(R.id.job_seeker_job_details_skillsRequired);
        addedByTextView = findViewById(R.id.job_seeker_job_details_addedBy);
        loadingSpinner = findViewById(R.id.job_seeker_job_details_loadingSpinner);
        contactButton = findViewById(R.id.job_seeker_job_details_contactButton);
        locationIcon = findViewById(R.id.job_seeker_job_details_locationIcon);
        jobTypeIcon = findViewById(R.id.job_seeker_job_details_jobTypeIcon);
        serviceRequiredIcon = findViewById(R.id.job_seeker_job_details_serviceRequiredIcon);
    }

    private void loadJobDetails() {
        loadingSpinner.setVisibility(View.VISIBLE);

        String id = getIntent().getStringExtra("jobId");

        Thread t = new Thread(() -> {
            jobSeekerService.getJobDetails(id).addOnFailureListener(command -> {
                Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG).show();
            }).addOnSuccessListener(command -> {
                data = command.toObjects(JobPostViewModel.class).get(0);

                jobDescriptionTextView.setText(data.getJobDescription());
                cityTextView.setText(data.getCity());
                jobTypeTextView.setText(data.getJobType());
                serviceRequiredTextView.setText(data.getServiceRequired());
                skillsRequiredTextView.setText(data.getSkillsRequired());
                addedByUserId = data.getCreatedBy();

                userService
                        .getUserDetails(data.getCreatedBy())
                        .addOnSuccessListener(documentSnapshot -> {
                            User user = documentSnapshot.toObject(User.class);

                            addedByTextView.setText("Adaugat de : " + user.getUserProfile().getName());

                            loadingSpinner.setVisibility(View.GONE);
                            contactButton.setVisibility(View.VISIBLE);
                            jobTypeIcon.setVisibility(View.VISIBLE);
                            locationIcon.setVisibility(View.VISIBLE);
                            serviceRequiredIcon.setVisibility(View.VISIBLE);

                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "eroare", Toast.LENGTH_LONG);
                        });
            });
        });

        t.start();
    }
}
