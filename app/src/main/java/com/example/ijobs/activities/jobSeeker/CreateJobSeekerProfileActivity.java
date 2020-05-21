package com.example.ijobs.activities.jobSeeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ijobs.R;
import com.example.ijobs.activities.jobSeeker.fragments.IntroServicesListAdapter;
import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.JobSeekerProfileViewModel;
import com.example.ijobs.viewmodels.ServiceOfferListItemViewModel;

import java.util.Arrays;
import java.util.List;

public class CreateJobSeekerProfileActivity extends AppCompatActivity {

    private ListView servicesList;
    private EditText pastExperienceField;
    private EditText pastJobsField;
    private EditText skillsField;
    private IntroServicesListAdapter adapter;
    private ServiceOfferListItemViewModel[] listItems;
    private Button finalizeProfileButton;
    private UserService userService;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_seeker_profile);

        userService = new UserService();
        authService = new AuthService();

        initializeComponents();
    }

    public void onFinalizeProfileButtonClick(View view) {
        boolean formIsValid = validateFields();

        if (formIsValid) {
            List<String> servicesOffered = adapter.getSelectedValues();
            String pastExperience = pastExperienceField.getText().toString();
            String pastJobs = pastJobsField.getText().toString();
            String skills = skillsField.getText().toString();
            String userId = authService.getUser().getUid();

            JobSeekerProfileViewModel jobSeekerProfileViewModel = new JobSeekerProfileViewModel(userId, servicesOffered, pastExperience, pastJobs, skills);

            userService.updateJobSeekerProfile(jobSeekerProfileViewModel)
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG);
                    })
                    .addOnSuccessListener(aVoid -> {
                        Intent goToJobSeekerListingScreen = new Intent(this, JobSeekerListingScreen.class);
                        goToJobSeekerListingScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goToJobSeekerListingScreen);
                    });
        }
    }

    private void initializeComponents() {
        finalizeProfileButton = findViewById(R.id.create_job_seeker_profile_finalizeProfileButton);
        pastExperienceField = findViewById(R.id.create_job_seeker_profile_pastExperience_textInput);
        pastJobsField = findViewById(R.id.create_job_seeker_profile_previousJobs_textInput);
        skillsField = findViewById(R.id.create_job_seeker_profile_skills_textInput);

        initializeServicesOfferedList();
    }

    private void initializeServicesOfferedList() {
        listItems = generateListData();

        servicesList = findViewById(R.id.create_job_seeker_profile_services_list);

        adapter = new IntroServicesListAdapter(getBaseContext(), R.id.create_user_profile_service_item_text, listItems);
        servicesList.setAdapter(adapter);

    }

    private ServiceOfferListItemViewModel[] generateListData() {
        return Arrays
                .stream(getResources().getStringArray(R.array.servicesOffered))
                .map(service -> new ServiceOfferListItemViewModel(service))
                .toArray(ServiceOfferListItemViewModel[]::new);

    }

    private boolean validateFields() {
        boolean isValid = true;

        if (pastExperienceField.getText().toString().isEmpty()) {
            pastExperienceField.setError("Specificati experienta anterioara");
            isValid = false;
        }
        if (pastJobsField.getText().toString().isEmpty()) {
            pastJobsField.setError("Specificati locurile anterioare de munca");
            isValid = false;
        }
        if (skillsField.getText().toString().isEmpty()) {
            pastJobsField.setError("Specificati abilitatile dvs.");
            isValid = false;
        }

        if (adapter.getSelectedValues().size() == 0) {
            Toast.makeText(getApplicationContext(), "Nu ati specificat serviciile oferite", Toast.LENGTH_LONG);
            isValid = false;
        }

        return isValid;
    }
}
