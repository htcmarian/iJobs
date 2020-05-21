package com.example.ijobs.activities.jobRecruiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ijobs.R;
import com.example.ijobs.data.JobPost;
import com.example.ijobs.services.JobRecruiterService;

import java.util.Arrays;
import java.util.List;

public class CreateJobRecruiterAdActivity extends AppCompatActivity {

    private EditText cityTextInput;
    private Spinner serviceSpinnerInput;
    private Spinner jobTypeSpinnerInput;
    private EditText jobDescriptionInput;
    private EditText skillsInput;
    private TextView serviceRequiredErrorLabel;
    private TextView jobTypeRequiredErrorLabel;
    private JobRecruiterService jobRecruiterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_recruiter_ad);

        jobRecruiterService = new JobRecruiterService();

        initializeComponents();
    }

    private void initializeComponents() {
        cityTextInput = findViewById(R.id.create_job_recruiter_ad_city_inputText);
        serviceSpinnerInput = findViewById(R.id.create_job_recruiter_ad_offered_service_input);
        jobTypeSpinnerInput = findViewById(R.id.create_job_recruiter_ad_jobType_input);
        jobDescriptionInput = findViewById(R.id.create_job_recruiter_ad_description_inputText);
        skillsInput = findViewById(R.id.create_job_recruiter_ad_requiredSkills_input);
        serviceRequiredErrorLabel = findViewById(R.id.create_job_recruiter_ad_serviceOffered_errorLabel);
        jobTypeRequiredErrorLabel = findViewById(R.id.create_job_recruiter_ad_jobType_errorLabel);

        initializeServiceTypeComponent();
        initializeJobTypeComponent();
    }

    private void initializeJobTypeComponent() {
        String[] valArray = getResources().getStringArray(R.array.jobTypes);
        String[] options = new String[valArray.length + 1];

        options[0] = "Tip job";
        for (int i = 0; i < valArray.length; i++) {
            options[i + 1] = valArray[i];
        }

        ArrayAdapter<String> jobTypeSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        jobTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jobTypeSpinnerInput.setAdapter(jobTypeSpinnerAdapter);

        jobTypeSpinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobTypeRequiredErrorLabel.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initializeServiceTypeComponent() {
        String[] valArray = getResources().getStringArray(R.array.servicesOffered);
        String[] options = new String[valArray.length + 1];

        options[0] = "Tip serviciu";
        for (int i = 0; i < valArray.length; i++) {
            options[i + 1] = valArray[i];
        }

        ArrayAdapter<String> serviceOfferSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        serviceOfferSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        serviceSpinnerInput.setAdapter(serviceOfferSpinnerAdapter);

        serviceSpinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceRequiredErrorLabel.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onCreateJobRecruiterAdButtonClick(View view) {
        boolean isInputValid = validateInput();

        if (isInputValid) {
            String city = cityTextInput.getText().toString();
            String serviceRequired = serviceSpinnerInput.getSelectedItem().toString();
            String jobType = jobTypeSpinnerInput.getSelectedItem().toString();
            String jobDescription = jobDescriptionInput.getText().toString();
            String skillsRequired = skillsInput.getText().toString();

            JobPost ad = new JobPost(city, serviceRequired, jobType, jobDescription, skillsRequired);

            jobRecruiterService.createJobPost(ad)
                    .addOnFailureListener(command -> {
                        Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG).show();
                    })
                    .addOnSuccessListener(command -> {
                        Toast.makeText(getApplicationContext(), "Anuntul a fost adaugat", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed((Runnable) () -> {
                            Intent goBackToRecruiterListingScreen = new Intent(CreateJobRecruiterAdActivity.this, JobRecruiterListingScreen.class);
                            goBackToRecruiterListingScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(goBackToRecruiterListingScreen);
                            finish();
                        }, 2000);
                    });
        }
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (cityTextInput.getText().toString().isEmpty()) {
            cityTextInput.setError("Nu ati specificat orasul");
            isValid = false;
        }

        if (serviceSpinnerInput.getSelectedItemPosition() == 0) {
            serviceRequiredErrorLabel.setText("Nu ati specificat serviciul oferit");
            isValid = false;
        }

        if (jobTypeSpinnerInput.getSelectedItemPosition() == 0) {
            jobTypeRequiredErrorLabel.setText("Nu ati specificat tipul job-ului");
            isValid = false;
        }

        if (jobDescriptionInput.getText().toString().isEmpty()) {
            jobDescriptionInput.setError("Nu ati specificat o descriere");
            isValid = false;
        }
        if (skillsInput.getText().toString().isEmpty()) {
            skillsInput.setError("Nu ati specificat skill-urile necesare");
            isValid = false;
        }

        return isValid;
    }
}
