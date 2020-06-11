package com.example.ijobs.activities.jobSeeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ijobs.R;
import com.example.ijobs.activities.jobSeeker.fragments.JobSeekerListAdapter;
import com.example.ijobs.data.User;
import com.example.ijobs.services.JobSeekerService;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.JobPostViewModel;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobSeekerListingScreen extends AppCompatActivity {
    private ListView jobPostsList;
    private JobSeekerService jobSeekerService;
    private JobSeekerListAdapter adapter;
    private Spinner jobTypeSpinnerInput;
    private Spinner jobServiceOfferSpinnerInput;
    private String filterJobType;
    private String filterServiceType;
    private ArrayList<JobPostViewModel> jobPosts;
    private CardStackView jobCardStackView;
    private int currentJobIndex = 0;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_listing_screen);

        jobSeekerService = new JobSeekerService();
        userService = new UserService();
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

                userService.getCurrentUser() .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    List<String> userServicesOffered = user.getJobSeekerCv().getServicesOffered();
                    List<JobPostViewModel> jobsToDisplay = data.stream().filter(v -> userServicesOffered.contains(v.getServiceRequired())).collect(Collectors.toList());

                    jobPosts.clear();
                    jobPosts.addAll(jobsToDisplay);

                    adapter.notifyDataSetChanged();
                });
            });
        });

        t.start();
    }

    private void initializeComponents() {
//        jobPostsList = findViewById(R.id.job_seeker_listing_screen_job_list);
        jobTypeSpinnerInput = findViewById(R.id.job_seeker_listing_screen_jobTypeFilter);
        jobServiceOfferSpinnerInput = findViewById(R.id.job_seeker_listing_screen_jobServiceRequiredFilter);
        jobCardStackView = findViewById(R.id.job_seeker_listing_screen_jobCardStackView);

        adapter = new JobSeekerListAdapter(getBaseContext(), jobPosts);
        jobCardStackView.setAdapter(adapter);

        jobCardStackView.setLayoutManager(new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {

                if(direction == Direction.Right){
                    Intent goToJobSeekerListingScreen = new Intent(getApplicationContext(), JobSeekerJobDetailsActivity.class);
                    goToJobSeekerListingScreen.putExtra("jobId", jobPosts.get(currentJobIndex).getId());
                    goToJobSeekerListingScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(goToJobSeekerListingScreen);
                }

                if(Direction.HORIZONTAL.contains(direction)){
                    if(currentJobIndex == jobPosts.size()-1){
                        currentJobIndex = 0;
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        currentJobIndex++;
                    }
                }


            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        }));

        initializeJobTypeComponent();
        initializeServiceTypeComponent();
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
