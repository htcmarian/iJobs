package com.example.ijobs.activities.jobRecruiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ijobs.R;
import com.example.ijobs.activities.jobRecruiter.fragments.JobRecruiterSeekerListAdapter;
import com.example.ijobs.data.User;
import com.example.ijobs.services.JobRecruiterService;
import com.example.ijobs.viewmodels.jobRecruiter.JobRecruiterSeekerViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class JobRecruiterListingScreen extends AppCompatActivity {

    private JobRecruiterService jobRecruiterService;
    private ListView jobSeekerList;
    private JobRecruiterSeekerViewModel[] jobSeekerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_recruiter_listing_screen);

        jobRecruiterService = new JobRecruiterService();

        initializeComponents();
    }

    public void onCreateAdButtonClick(View view){
        Intent goBackToRecruiterListingScreen = new Intent(JobRecruiterListingScreen.this, CreateJobRecruiterAdActivity.class);
        startActivity(goBackToRecruiterListingScreen);
    }

    private void initializeComponents() {
        jobSeekerList = findViewById(R.id.job_recruiter_listing_screen_seeker_list);

        jobRecruiterService.getJobSeekers().get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot doc : task.getResult()){
                    List<User> jobSeekers = task.getResult().toObjects(User.class);

                    jobSeekerData = new JobRecruiterSeekerViewModel[jobSeekers.size()];

                    for(int i=0;i<jobSeekers.size();i++){
                        User jobSeeker = jobSeekers.get(i);
                        jobSeekerData[i] = new JobRecruiterSeekerViewModel(jobSeeker.getUserId(), jobSeeker.getUserProfile().getName(), jobSeeker.getUserProfile().getCity(), jobSeeker.getJobSeekerCv().getServicesOffered());
                    }

                    JobRecruiterSeekerListAdapter adapter = new JobRecruiterSeekerListAdapter(getBaseContext(), R.id.job_recruiter_list_seeker_list_item_name, jobSeekerData);

                    jobSeekerList.setAdapter(adapter);
                }
            }
        });

    }
}
