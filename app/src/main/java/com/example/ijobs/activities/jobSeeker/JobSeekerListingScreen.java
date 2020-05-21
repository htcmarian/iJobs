package com.example.ijobs.activities.jobSeeker;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ijobs.R;
import com.example.ijobs.activities.jobSeeker.fragments.JobPostListAdapter;
import com.example.ijobs.services.JobSeekerService;
import com.example.ijobs.viewmodels.JobPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class JobSeekerListingScreen extends AppCompatActivity {
    private ListView jobPostsList;
    private JobSeekerService jobSeekerService;
    private JobPostListAdapter adapter;
    private Spinner jobTypeSpinnerInput;
    private Spinner jobServiceOfferSpinnerInput;
    private String filterJobType;
    private String filterServiceType;
    private ArrayList<JobPostViewModel> jobPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_listing_screen);

        jobSeekerService = new JobSeekerService();
        jobPosts = new ArrayList<>();

        initializeComponents();

        loadJobPosts();
    }

    private void loadJobPosts() {
        Thread t = new Thread(() -> {
            jobSeekerService.getRecommendedJobPosts(filterJobType, filterServiceType).addOnFailureListener(command -> {
                Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG).show();
            }).addOnSuccessListener(command -> {
                List<JobPostViewModel> data = command.toObjects(JobPostViewModel.class);

                jobPosts.clear();
                jobPosts.addAll(data);

                adapter.notifyDataSetChanged();
            });
        });

        t.start();
    }

    private void initializeComponents() {
        jobPostsList = findViewById(R.id.job_seeker_listing_screen_job_list);
        jobTypeSpinnerInput = findViewById(R.id.job_seeker_listing_screen_jobTypeFilter);
        jobServiceOfferSpinnerInput = findViewById(R.id.job_seeker_listing_screen_jobServiceRequiredFilter);

        initializeJobTypeComponent();
        initializeServiceTypeComponent();

        adapter = new JobPostListAdapter(getBaseContext(), R.id.job_seeker_list_item_jobTitleText, jobPosts);
        jobPostsList.setAdapter(adapter);
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
                filterJobType = position == 0 ? null : options[position];
                loadJobPosts();
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

        jobServiceOfferSpinnerInput.setAdapter(serviceOfferSpinnerAdapter);

        jobServiceOfferSpinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterServiceType = position == 0 ? null : options[position];
                loadJobPosts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
