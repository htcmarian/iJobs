package com.example.ijobs.activities.jobRecruiter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ijobs.R;
import com.example.ijobs.data.User;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.JobPostViewModel;
import com.example.ijobs.viewmodels.UserProfileViewModel;

public class JobRecruiterSeekerDetailsActivity extends AppCompatActivity {

    private TextView phoneNumberTextView;
    private ImageView phoneNumberLabel;
    private TextView pastJobsTextView;
    private TextView pastJobsLabel;
    private TextView pastExperienceTextView;
    private TextView pastExperienceLabel;
    private TextView skillsTextView;
    private TextView skillsLabel;
    private TextView nameTextView;
    private TextView locationTextView;
    private ImageView locationLabel;
    private ProgressBar loadingSpinner;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_recruiter_seeker_details);

        initializeComponents();
        
        userService = new UserService();

        loadSeekerProfile();
    }

    private void initializeComponents() {
        phoneNumberTextView = findViewById(R.id.job_recruiter_seeker_profile_phoneNumberTextView);
        phoneNumberLabel = findViewById(R.id.job_recruiter_seeker_profile_phoneNumberLabel);
        pastJobsTextView = findViewById(R.id.job_recruiter_seeker_profile_pastJobsTextView);
        pastJobsLabel = findViewById(R.id.job_recruiter_seeker_profile_pastJobsLabel);
        pastExperienceTextView = findViewById(R.id.job_recruiter_seeker_profile_pastExperienceTextView);
        pastExperienceLabel = findViewById(R.id.job_recruiter_seeker_profile_pastExperienceLabel);
        skillsTextView = findViewById(R.id.job_recruiter_seeker_profile_skillsTextView);
        skillsLabel = findViewById(R.id.job_recruiter_seeker_profile_skillsLabel);
        nameTextView = findViewById(R.id.job_recruiter_seeker_profile_nameTextView);
        locationTextView = findViewById(R.id.job_recruiter_seeker_profile_locationTextView);
        locationLabel = findViewById(R.id.job_recruiter_seeker_profile_locationLabel);
        loadingSpinner = findViewById(R.id.job_recruiter_seeker_profile_loadingSpinner);
    }

    private void loadSeekerProfile() {
        loadingSpinner.setVisibility(View.VISIBLE);

        String userId = getIntent().getStringExtra("userId");

        Thread t = new Thread(() -> {
            userService.getUserDetails(userId).addOnFailureListener(command -> {
                Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG).show();
            }).addOnSuccessListener(command -> {
                User data = command.toObject(User.class);

                phoneNumberLabel.setVisibility(View.VISIBLE);
                phoneNumberTextView.setText(data.getUserProfile().getPhoneNumber());

                pastJobsLabel.setVisibility(View.VISIBLE);
                pastJobsTextView.setText(data.getJobSeekerCv().getPastJobs());

                pastExperienceLabel.setVisibility(View.VISIBLE);
                pastExperienceTextView.setText(data.getJobSeekerCv().getPastExperience());

                skillsLabel.setVisibility(View.VISIBLE);
                skillsTextView.setText(data.getJobSeekerCv().getSkills());

                nameTextView.setVisibility(View.VISIBLE);
                nameTextView.setText(data.getUserProfile().getName());

                locationLabel.setVisibility(View.VISIBLE);
                locationTextView.setText(data.getUserProfile().getAddress() + "," + data.getUserProfile().getCity());

                loadingSpinner.setVisibility(View.GONE);
            });
        });

        t.start();
    }
}
