package com.example.ijobs.activities.jobRecruiter.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ijobs.R;
import com.example.ijobs.activities.jobRecruiter.JobRecruiterSeekerDetailsActivity;
import com.example.ijobs.activities.jobSeeker.JobSeekerJobDetailsActivity;
import com.example.ijobs.services.ImageProvider;
import com.example.ijobs.viewmodels.jobRecruiter.JobRecruiterSeekerViewModel;

import java.util.List;

public class JobRecruiterSeekerListAdapter extends ArrayAdapter<JobRecruiterSeekerViewModel>{
    private final Context context;
    private final JobRecruiterSeekerViewModel[] values;

    public JobRecruiterSeekerListAdapter(Context context, int textViewResourceId, JobRecruiterSeekerViewModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.job_recruiter_list_seeker_list_item, parent, false);

        TextView nameTextView = rowView.findViewById(R.id.job_recruiter_list_seeker_list_item_name);
        TextView locationTextView = rowView.findViewById(R.id.job_recruiter_list_seeker_list_item_location);
        TextView servicesOfferedTextView = rowView.findViewById(R.id.job_recruiter_list_seeker_list_item_servicesOffered);
        ImageView serviceOfferedImageView = rowView.findViewById(R.id.job_recruiter_list_seeker_list_item_jobTypeImage);

        String name = values[position].getName();
        String location = values[position].getCity();
        List<String> servicesOffered = values[position].getServicesOffered();
        String servicesOfferedEnumeration = String.join(", ", servicesOffered).trim();
        String serviceOfferedImageName = servicesOfferedEnumeration.contains(",") ? "multiple" : servicesOfferedEnumeration;
        Uri imageUrl = ImageProvider.getImageUri(serviceOfferedImageName);

        nameTextView.setText(name);
        locationTextView.setText(location);
        servicesOfferedTextView.setText(String.join(", ", servicesOffered));
        serviceOfferedImageView.setImageURI(imageUrl);

        rowView.setOnClickListener(v -> {
            Intent jobRecruiterSeekerDetailsActivity = new Intent(getContext(), JobRecruiterSeekerDetailsActivity.class);
            jobRecruiterSeekerDetailsActivity.putExtra("userId", values[position].getUserId());
            jobRecruiterSeekerDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(jobRecruiterSeekerDetailsActivity);
        });

        return rowView;
    }
}
